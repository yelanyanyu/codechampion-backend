package com.yelanyanyu.codechampion.judge.codesandbox;

import com.yelanyanyu.codechampion.judge.codesandbox.model.ExecuteCodeRequest;
import com.yelanyanyu.codechampion.judge.codesandbox.model.ExecuteCodeResponse;
import com.yelanyanyu.codechampion.model.enums.QuestionSubmitLanguageEnum;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author yelanyanyu@zjxu.edu.cn
 * @version 1.0
 */
@SpringBootTest
class CodeSandboxTest {
    @Value("${codesandbox.type}")
    private String type;

    @Test
    void executeCode() {
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        String code = "int main() {return 0;}";
        String language = QuestionSubmitLanguageEnum.GOLANG.getValue();
        List<String> input = Arrays.asList("1 2", "3, 4");

        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .input(input)
                .build();
        assertNotNull(codeSandbox);
        ExecuteCodeResponse executeCodeResponse = new CodeSandboxProxy(codeSandbox).execute(executeCodeRequest);
        assertNotNull(executeCodeResponse);
    }
}
