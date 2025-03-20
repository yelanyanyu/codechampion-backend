package com.yelanyanyu.codechampion.judge.codesandbox;

import cn.hutool.core.lang.Assert;
import com.yelanyanyu.codechampion.judge.codesandbox.model.ExecuteCodeRequest;
import com.yelanyanyu.codechampion.judge.codesandbox.model.ExecuteCodeResponse;
import com.yelanyanyu.codechampion.model.enums.QuestionSubmitLanguageEnum;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
                .inputList(input)
                .build();
        assertNotNull(codeSandbox);
        ExecuteCodeResponse executeCodeResponse = new CodeSandboxProxy(codeSandbox).execute(executeCodeRequest);
        assertNotNull(executeCodeResponse);
    }

    @Test
    void executeCodeByProxy() {
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        codeSandbox = new CodeSandboxProxy(codeSandbox);
        String code = "public class Main {\n" +
                "    public static void main(String[] args) {\n" +
                "        int a=Integer.parseInt(args[0]);\n" +
                "        int b=Integer.parseInt(args[1]);\n" +
                "        System.out.println(\"结果\"+(a+b));\n" +
                "    }\n" +
                "}\n";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        List<String> inputList = Arrays.asList("1 2", "3 4");
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.execute(executeCodeRequest);
        Assert.notNull(executeCodeResponse);
        log.info(executeCodeResponse.toString());
    }
}
