package com.yelanyanyu.codechampion.judge;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.yelanyanyu.codechampion.model.dto.question.JudgeCase;
import com.yelanyanyu.codechampion.model.dto.question.JudgeConfig;
import com.yelanyanyu.codechampion.judge.codesandbox.model.JudgeInfo;
import com.yelanyanyu.codechampion.model.entity.Question;
import com.yelanyanyu.codechampion.model.entity.QuestionSubmit;
import com.yelanyanyu.codechampion.model.enums.JudgeInfoMessageEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

/**
 * 实现 Java 的判题策略
 *
 * @author yelanyanyu@zjxu.edu.cn
 * @version 1.0
 */
@Slf4j
public class JavaLanguageJudgeStrategyImpl implements JudgeStrategy {
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();


        Long memory = Optional.ofNullable(judgeInfo.getMemory()).orElse(0L);
        Long time = Optional.ofNullable(judgeInfo.getTime()).orElse(0L);
        List<String> inputList = judgeContext.getInputList();
        List<String> outputList = judgeContext.getOutputList();
        Question question = judgeContext.getQuestion();
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
        JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.ACCEPT;
        JudgeInfo judgeInfoResponse = new JudgeInfo();
        judgeInfoResponse.setMemory(memory);
        judgeInfoResponse.setTime(time);

        String message = judgeInfo.getMessage();
        // 判断是否编译错误
        if (message != null && message.equals(JudgeInfoMessageEnum.COMPILE_ERROR.getValue())) {
            judgeInfoResponse.setMessage(JudgeInfoMessageEnum.COMPILE_ERROR.getValue());
            return judgeInfoResponse;
        }
        //先判断沙箱执行的结果输出数量是否和预期输出数量相等
        if (outputList.size() != inputList.size()) {
            log.error("outputList: {}, inputList: {}", outputList, inputList);
            judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }

        //判断题目限制

        String judgeConfigStr = question.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);
        Long needMemoryLimit = judgeConfig.getMemoryLimit() * 1024 * 1024;
        Long needTimeLimit = judgeConfig.getTimeLimit();
        if (memory > needMemoryLimit) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        //Java程序本身需要额外执行10秒钟
        long JAVA_PROGRAM_TIME_COST = 1000L;
        if ((time - JAVA_PROGRAM_TIME_COST) > needTimeLimit) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }

        //判断每一项输出和预期输出是否相等
        for (int i = 0; i < judgeCaseList.size(); i++) {
            JudgeCase judgeCase = judgeCaseList.get(i);
            String output = outputList.get(i);
            if (StrUtil.isEmpty(output) || !judgeCase.getOutput().trim().equals(outputList.get(i).trim())) {
                log.error("judgeCase.output: {}, outputList.get(i): {}", judgeCase.getOutput(), outputList.get(i));
                judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
                judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
                return judgeInfoResponse;
            }
        }

        judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
        return judgeInfoResponse;
    }
}
