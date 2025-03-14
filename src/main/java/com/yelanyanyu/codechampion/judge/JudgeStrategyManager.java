package com.yelanyanyu.codechampion.judge;


import com.yelanyanyu.codechampion.judge.codesandbox.model.JudgeInfo;
import com.yelanyanyu.codechampion.model.enums.QuestionSubmitLanguageEnum;
import org.springframework.stereotype.Component;

/**
 * 用来管理不同的判题策略
 *
 * @author yelanyanyu@zjxu.edu.cn
 * @version 1.0
 */
@Component
public class JudgeStrategyManager {
    JudgeInfo doJudge(JudgeContext judgeContext) {
        String language = judgeContext.getQuestionSubmit().getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategyImpl();
        if (QuestionSubmitLanguageEnum.JAVA.getValue().equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategyImpl();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
