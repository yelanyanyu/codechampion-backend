package com.yelanyanyu.codechampion.judge.codesandbox.impl;


import com.yelanyanyu.codechampion.judge.codesandbox.CodeSandbox;
import com.yelanyanyu.codechampion.judge.codesandbox.model.ExecuteCodeRequest;
import com.yelanyanyu.codechampion.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * @author yelanyanyu@zjxu.edu.cn
 * @version 1.0
 */
public class RemoteCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse execute(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("RemoteCodeSandbox");
        return null;
    }
}
