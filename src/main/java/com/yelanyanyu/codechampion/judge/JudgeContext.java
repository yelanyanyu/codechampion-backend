package com.yelanyanyu.codechampion.judge;


import com.yelanyanyu.codechampion.model.dto.question.JudgeCase;
import com.yelanyanyu.codechampion.model.dto.questionsubmit.JudgeInfo;
import com.yelanyanyu.codechampion.model.entity.Question;
import com.yelanyanyu.codechampion.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * 策略判断中的传递参数的包装类
 *
 * @author yelanyanyu@zjxu.edu.cn
 * @version 1.0
 */
@Data
public class JudgeContext {
    /**
     * 输入给代码沙箱的输入
     */
    private List<String> inputList;
    /**
     * 代码沙箱的程序执行输出
     */
    private List<String> outputList;
    /**
     * 代码沙箱输入的 JSON 解析结果
     */
    private List<JudgeCase> judgeCaseList;
    private JudgeInfo judgeInfo;
    private Question question;
    private QuestionSubmit questionSubmit;
}
