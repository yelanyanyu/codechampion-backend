package com.yelanyanyu.codechampion.judge.codesandbox.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * @author yelanyanyu@zjxu.edu.cn
 * @version 1.0
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExecuteCodeRequest {
    private List<String> inputList;
    private String code;
    private String language;
}
