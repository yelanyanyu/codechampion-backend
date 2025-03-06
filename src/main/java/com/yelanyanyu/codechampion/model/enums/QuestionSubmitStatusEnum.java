package com.yelanyanyu.codechampion.model.enums;

import io.swagger.models.auth.In;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Enum representing the possible statuses of code question submissions in the judging process.
 * Each status represents a different stage or outcome in the code evaluation workflow.
 *
 * @author yelanyanyu
 * @version 1.0
 */
@Getter
public enum QuestionSubmitStatusEnum {

    /**
     * The submission has been received but is waiting in queue to be processed.
     */
    WAITING("等待中", 0),

    /**
     * The submission is currently being evaluated by the judge system.
     */
    RUNNING("判题中", 1),

    /**
     * The submission was successfully evaluated and passed all test cases.
     */
    SUCCEED("成功", 2),

    /**
     * The submission was evaluated but failed one or more test cases.
     */
    FAILED("失败", 3);

    /**
     * The human-readable text description of the status (in Chinese).
     */
    private final String text;

    /**
     * The integer value representing the status, used for storage and comparison.
     */
    private final Integer value;

    /**
     * Constructor for the enum constants.
     *
     * @param text  The human-readable text description of the status
     * @param value The integer value representing the status
     */
    QuestionSubmitStatusEnum(String text, Integer value) {
        this.text = text;
        this.value = value;
    }

    /**
     * Returns a list of all the integer values defined in this enum.
     * This is useful for validation purposes to check if a given integer
     * represents a valid status.
     *
     * @return A list containing all integer values defined in the enum
     */
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * Finds and returns the enum constant that matches the provided value.
     *
     * @param value The string value to search for
     * @return The matching enum constant, or null if no match is found or value is empty
     */
    public static QuestionSubmitStatusEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (QuestionSubmitStatusEnum anEnum : QuestionSubmitStatusEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }
}
