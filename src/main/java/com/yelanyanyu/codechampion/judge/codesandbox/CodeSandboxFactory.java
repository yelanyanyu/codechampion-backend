package com.yelanyanyu.codechampion.judge.codesandbox;


import com.yelanyanyu.codechampion.judge.codesandbox.impl.ExampleCodeSandbox;
import com.yelanyanyu.codechampion.judge.codesandbox.impl.RemoteCodeSandbox;
import com.yelanyanyu.codechampion.judge.codesandbox.impl.ThirdPartyCodeSandbox;

/**
 * @author yelanyanyu@zjxu.edu.cn
 * @version 1.0
 */
public class CodeSandboxFactory {
    public static CodeSandbox newInstance(String type) {
        switch (type) {
            case "example":
                return new ExampleCodeSandbox();
            case "remote":
                return new RemoteCodeSandbox();
            case "thirdParty":
                return new ThirdPartyCodeSandbox();
            default: return null;
        }
    }
}
