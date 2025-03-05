package com.yelanyanyu.codechampion.model.dto.question;

import lombok.Data;

/**
 * The JudgeCase class represents a test case used for code evaluation in the Code Champion system.
 * Each judge case consists of an input value that is fed to the code being tested,
 * and an expected output value used to validate the correctness of the solution.
 * <p>
 * This class is used as a Data Transfer Object (DTO) for transferring test case data
 * between different components of the application.
 *
 * @author yelanyanyu@zjxu.edu.cn
 * @version 1.0
 */
@Data
public class JudgeCase {
    /**
     * The input test case.
     * Contains the input data that will be provided to the solution being evaluated.
     */
    private String input;

    /**
     * The output test case.
     * Contains the expected output data that a correct solution should produce.
     */
    private String output;
}
