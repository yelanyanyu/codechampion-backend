package com.yelanyanyu.codechampion.judge;


import cn.hutool.json.JSONUtil;
import com.qcloud.cos.utils.StringUtils;
import com.yelanyanyu.codechampion.common.ErrorCode;
import com.yelanyanyu.codechampion.exception.BusinessException;
import com.yelanyanyu.codechampion.judge.codesandbox.CodeSandbox;
import com.yelanyanyu.codechampion.judge.codesandbox.CodeSandboxFactory;
import com.yelanyanyu.codechampion.judge.codesandbox.CodeSandboxProxy;
import com.yelanyanyu.codechampion.judge.codesandbox.model.ExecuteCodeRequest;
import com.yelanyanyu.codechampion.judge.codesandbox.model.ExecuteCodeResponse;
import com.yelanyanyu.codechampion.model.dto.question.JudgeCase;
import com.yelanyanyu.codechampion.judge.codesandbox.model.JudgeInfo;
import com.yelanyanyu.codechampion.model.entity.Question;
import com.yelanyanyu.codechampion.model.entity.QuestionSubmit;
import com.yelanyanyu.codechampion.model.enums.QuestionSubmitStatusEnum;
import com.yelanyanyu.codechampion.service.QuestionService;
import com.yelanyanyu.codechampion.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yelanyanyu@zjxu.edu.cn
 * @version 1.0
 */
@Service
public class JudgeServiceImpl implements JudgeService {
    @Resource
    private QuestionSubmitService questionSubmitService;
    @Resource
    private QuestionService questionService;
    @Resource
    private JudgeStrategyManager judgeStrategyManager;
    @Value("${codesandbox.type:example}")
    private String type;
    @Override
    public QuestionSubmit doJudgeById(long questionSubmitId) {
        /*
            1. 通过 questionSubmitId 得到题目的提交信息；
            2. 如果题目的提交状态不是等待中，那么就可以不同进行判题了；如果是等待中，那么就执行第三步；
            3. 将判题信息改为判题中;
            4. 并将判题信息提交给代码沙箱，异步等待代码沙箱的结果。
            5. 根据不同的代码语言，调用不同的判题策略，得到判题的结果 judgeInfo
            6. 更改题目的判题信息，返回给前端；
         */
        // 第一步
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        Long id = questionSubmit.getId();
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        Integer status = questionSubmit.getStatus();
        Long questionId = questionSubmit.getQuestionId();
        // 第二步
        if (!status.equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            return questionSubmit;
        }
        // 第三步
        QuestionSubmit newquestionSubmit = new QuestionSubmit();
        newquestionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        newquestionSubmit.setId(id);
        boolean isUpdate = questionSubmitService.updateById(newquestionSubmit);
        if (!isUpdate) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目信息更新错误");
        }
        // 4
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "非法提交信息");
        }
        String judgeCase = question.getJudgeCase();
        if (StringUtils.isNullOrEmpty(judgeCase)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "非法提交信息");
        }
        List<JudgeCase> judgeCases = JSONUtil.toList(judgeCase, JudgeCase.class);
        List<String> input = judgeCases.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        // 交给沙箱判题
        ExecuteCodeResponse executeCodeResponse = sentToSandbox(code, language, input);
        // 5
        JudgeContext judgeContext = new JudgeContext();
        List<String> output = executeCodeResponse.getOutput();
        String message = executeCodeResponse.getMessage();
        JudgeInfo judgeInfo = executeCodeResponse.getJudgeInfo();

        judgeContext.setJudgeInfo(judgeInfo);
        judgeContext.setInputList(input);
        judgeContext.setOutputList(output);
        judgeContext.setJudgeCaseList(judgeCases);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);

        JudgeInfo newJudgeInfo = judgeStrategyManager.doJudge(judgeContext);

        newquestionSubmit = new QuestionSubmit();
        newquestionSubmit.setId(id);
        newquestionSubmit.setJudgeInfo(JSONUtil.toJsonStr(newJudgeInfo));
        newquestionSubmit.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        boolean update = questionSubmitService.updateById(newquestionSubmit);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目信息更新错误");
        }
        return questionSubmitService.getById(questionId);
    }

    private ExecuteCodeResponse sentToSandbox(String code, String language, List<String> input) {
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);

        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .input(input)
                .build();
        return new CodeSandboxProxy(codeSandbox).execute(executeCodeRequest);
    }
}
