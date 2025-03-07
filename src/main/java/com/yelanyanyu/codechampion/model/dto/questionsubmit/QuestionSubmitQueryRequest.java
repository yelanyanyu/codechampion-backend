package com.yelanyanyu.codechampion.model.dto.questionsubmit;


import com.baomidou.mybatisplus.annotation.TableField;
import com.yelanyanyu.codechampion.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Request DTO for querying code submissions with filtering and pagination capabilities.
 * <p>
 * This class extends PageRequest to inherit pagination functionality and adds specific
 * filtering criteria for question submissions such as programming language, submission status,
 * question ID, and user ID. It's used to filter and retrieve submissions from the database
 * based on these criteria.
 *
 * @author yelanyanyu@zjxu.edu.cn
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionSubmitQueryRequest extends PageRequest implements Serializable {
    /**
     * Serialization version ID for maintaining compatibility during class evolution
     */
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * Programming language filter for submissions.
     * When specified, only submissions in this language will be included in results.
     */
    private String language;

    /**
     * Submission status filter.
     * Used to filter submissions by their processing status (e.g., "Waiting", "Accepted", "Error").
     * Values correspond to those defined in QuestionSubmitStatusEnum.
     */
    private String status;

    /**
     * Question ID filter.
     * When specified, only submissions for this specific question will be included in results.
     */
    private Long questionId;

    /**
     * User ID filter.
     * When specified, only submissions made by this user will be included in results.
     */
    private Long userId;
}
