package com.yelanyanyu.codechampion.judge.codesandbox.impl;


import com.yelanyanyu.codechampion.judge.codesandbox.CodeSandbox;
import com.yelanyanyu.codechampion.judge.codesandbox.model.ExecuteCodeRequest;
import com.yelanyanyu.codechampion.judge.codesandbox.model.ExecuteCodeResponse;
import com.yelanyanyu.codechampion.model.dto.questionsubmit.JudgeInfo;
import com.yelanyanyu.codechampion.model.enums.QuestionSubmitStatusEnum;

import java.util.Arrays;
import java.util.List;

/**
 * @author yelanyanyu@zjxu.edu.cn
 * @version 1.0
 */
public class ExampleCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse execute(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("ExampleCodeSandbox");
        List<String> input = executeCodeRequest.getInput();
        String code = executeCodeRequest.getCode();
        String language = executeCodeRequest.getLanguage();

        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutput(Arrays.asList("1 2"));
        executeCodeResponse.setMessage("Hello World");
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());

        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage("Hello World");
        judgeInfo.setTime(1000L);
        judgeInfo.setMemory(1000L);

        executeCodeResponse.setJudgeInfo(judgeInfo);

        return executeCodeResponse;
    }
}
