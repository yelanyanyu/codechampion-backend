package com.yelanyanyu.codechampion.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;

/**
 * Enum representing the roles that users can have in the system.
 * Each role defines different levels of permissions and access controls
 * for user operations within the Code Champion application.
 */
public enum UserRoleEnum {

    /**
     * Regular user with standard permissions.
     * This is the default role for newly registered users.
     */
    USER("用户", "user"),

    /**
     * Administrator role with elevated permissions.
     * Admins can manage other users, content, and system settings.
     */
    ADMIN("管理员", "admin"),

    /**
     * Banned user with restricted permissions.
     * Users with this role have limited or no access to system features.
     */
    BAN("被封号", "ban");

    /**
     * The human-readable text description of the role (in Chinese).
     */
    private final String text;

    /**
     * The string value representing the role, used for storage and comparison.
     */
    private final String value;

    /**
     * Constructor for the enum constants.
     *
     * @param text  The human-readable text description of the role
     * @param value The string value representing the role
     */
    UserRoleEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * Returns a list of all the string values defined in this enum.
     * Useful for validation to check if a given string represents a valid role.
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
    public static UserRoleEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (UserRoleEnum anEnum : UserRoleEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    /**
     * Gets the string value of this role.
     *
     * @return The string value representing this role
     */
    public String getValue() {
        return value;
    }

    /**
     * Gets the text description of this role.
     *
     * @return The human-readable description of this role
     */
    public String getText() {
        return text;
    }
}
