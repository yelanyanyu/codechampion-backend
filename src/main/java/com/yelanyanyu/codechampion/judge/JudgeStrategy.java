package com.yelanyanyu.codechampion.judge;


import com.yelanyanyu.codechampion.judge.codesandbox.model.JudgeInfo;

/**
 * 判题策略接口，用于实现不同语言的不同判题策略，使用了策略模式的设计模式
 *
 * @author yelanyanyu@zjxu.edu.cn
 * @version 1.0
 */
public interface JudgeStrategy {
    /**
     * 根据代码沙箱的判题结果得到判题信息，具体流程如下：
     * 1. 判断程序输出用例的长度是否与JudgeCase 中的output相同；
     * 2. 判断 输出结果 和 output是否相同，如果不同则输出错误信息；
     * 3. 判断题目的限制是否满足要求，具体来说就是将 JudgeContext.judgeInfo 中的内容与 submit 中的info相比较
     * 4. 判断其他异常情况；
     *
     * @param judgeContext 判题信息，这个信息由代码沙箱返回传入
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext);
}
