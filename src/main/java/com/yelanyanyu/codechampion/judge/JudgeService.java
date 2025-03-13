package com.yelanyanyu.codechampion.judge;


import com.yelanyanyu.codechampion.model.entity.QuestionSubmit;

/**
 * @author yelanyanyu@zjxu.edu.cn
 * @version 1.0
 */
public interface JudgeService {
    /**
     * 将问题提交记录交给代码沙箱，获取结果后返回给用户判题结果
     *
     * @param questionSubmitId
     * @return
     */
    QuestionSubmit doJudgeById(long questionSubmitId);
}
