package com.yelanyanyu.codechampion.model.enums;

import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Enum representing the programming languages supported for code submissions
 * in the Code Champion judging system.
 * <p>
 * This enum provides constants for the available programming languages that users
 * can submit solutions in, along with utility methods for validation and conversion.
 *
 * @author yelanyanyu
 * @version 1.0
 */
@Getter
public enum QuestionSubmitLanguageEnum {

    /**
     * Java programming language
     */
    JAVA("java", "java"),

    /**
     * C++ programming language
     */
    CPP("C++", "C++"),

    /**
     * Go programming language
     */
    GOLANG("golang", "golang");

    /**
     * The human-readable text description of the programming language
     */
    private final String text;

    /**
     * The string value representing the programming language, used for storage and comparison
     */
    private final String value;

    /**
     * Constructor for the enum constants.
     *
     * @param text  The human-readable text description of the language
     * @param value The string value representing the language
     */
    QuestionSubmitLanguageEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * Returns a list of all the string values defined in this enum.
     * Useful for validation to check if a given string represents a supported language.
     *
     * @return A list containing all string values defined in the enum
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * Finds and returns the enum constant that matches the provided value.
     *
     * @param value The string value to search for
     * @return The matching enum constant, or null if no match is found or value is empty
     */
    public static QuestionSubmitLanguageEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (QuestionSubmitLanguageEnum anEnum : QuestionSubmitLanguageEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }
}
