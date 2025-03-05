package com.yelanyanyu.codechampion.model.dto.question;

import lombok.Data;

/**
 * The JudgeConfig class represents configuration parameters for the code execution environment
 * in the Code Champion judging system.
 * <p>
 * This class defines the resource constraints that will be applied to submitted code during
 * evaluation, including time, memory, and stack limitations.
 *
 * @author yelanyanyu@zjxu.edu.cn
 * @version 1.0
 */
@Data
public class JudgeConfig {
    /**
     * The maximum execution time allowed for a submitted solution, measured in milliseconds.
     * Solutions exceeding this time limit will result in a "Time Limit Exceeded" judgment.
     */
    private Long timeLimit;

    /**
     * The maximum memory usage allowed for a submitted solution, measured in bytes.
     * Solutions exceeding this memory limit will result in a "Memory Limit Exceeded" judgment.
     */
    private Long memoryLimit;

    /**
     * The maximum stack size allowed for a submitted solution, measured in bytes.
     * Solutions exceeding this stack limit will result in a "Stack Overflow" judgment.
     */
    private Long stackLimit;
}
