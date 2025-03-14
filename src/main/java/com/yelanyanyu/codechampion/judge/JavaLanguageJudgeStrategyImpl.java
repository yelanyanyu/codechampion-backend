package com.yelanyanyu.codechampion.judge;


import cn.hutool.json.JSONUtil;
import com.yelanyanyu.codechampion.model.dto.question.JudgeCase;
import com.yelanyanyu.codechampion.model.dto.question.JudgeConfig;
import com.yelanyanyu.codechampion.judge.codesandbox.model.JudgeInfo;
import com.yelanyanyu.codechampion.model.entity.Question;
import com.yelanyanyu.codechampion.model.entity.QuestionSubmit;
import com.yelanyanyu.codechampion.model.enums.JudgeInfoMessageEnum;

import java.util.List;

/**
 * 实现 Java 的判题策略
 * @author yelanyanyu@zjxu.edu.cn
 * @version 1.0
 */
public class JavaLanguageJudgeStrategyImpl implements JudgeStrategy {
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        List<String> inputList = judgeContext.getInputList();
        List<String> outputList = judgeContext.getOutputList();
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
        JudgeInfo resultJudgeInfo = judgeContext.getJudgeInfo();
        Question question = judgeContext.getQuestion();
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String expectedInfo = questionSubmit.getJudgeInfo();
        // 用于方法返回的 info
        JudgeInfo newJudgeInfo = new JudgeInfo();
        newJudgeInfo.setMemory(resultJudgeInfo.getMemory());
        newJudgeInfo.setTime(resultJudgeInfo.getTime());
        newJudgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPT.getValue());
        // 1
        if (inputList.size() != outputList.size()) {
            newJudgeInfo.setMessage(JudgeInfoMessageEnum.WRONG_ANSWER.getValue());
            return newJudgeInfo;
        }
        // 2
        for (int i = 0; i < outputList.size(); i++) {
            JudgeCase judgeCase = judgeCaseList.get(i);
            String output = outputList.get(i);
            if (!judgeCase.getOutput().equals(output)) {
                newJudgeInfo.setMessage(JudgeInfoMessageEnum.WRONG_ANSWER.getValue());
                return newJudgeInfo;
            }
        }
        // 3
        String judgeConfigStr = question.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);
        Long timeLimit = judgeConfig.getTimeLimit();
        Long memoryLimit = judgeConfig.getMemoryLimit();
        Long stackLimit = judgeConfig.getStackLimit();

        String resMessage = resultJudgeInfo.getMessage();
        Long resTime = resultJudgeInfo.getTime();
        Long resMemory = resultJudgeInfo.getMemory();

        Long JAVA_TIME_LIMIT = timeLimit + 1000L;
        if (resTime > JAVA_TIME_LIMIT) {
            newJudgeInfo.setMessage(JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED.getValue());
            return newJudgeInfo;
        }
        if (resMemory > memoryLimit) {
            newJudgeInfo.setMessage(JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED.getValue());
            return newJudgeInfo;
        }
        return newJudgeInfo;
    }
}
