package com.yelanyanyu.codechampion.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yelanyanyu.codechampion.exception.BusinessException;
import com.yelanyanyu.codechampion.model.dto.question.QuestionQueryRequest;
import com.yelanyanyu.codechampion.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.yelanyanyu.codechampion.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.yelanyanyu.codechampion.model.entity.Question;
import com.yelanyanyu.codechampion.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yelanyanyu.codechampion.model.entity.User;
import com.yelanyanyu.codechampion.model.vo.QuestionSubmitVO;
import com.yelanyanyu.codechampion.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
 * Service interface for managing code submissions to programming questions.
 * <p>
 * This interface defines methods for handling the submission process, accessing submission history,
 * and performing operations on QuestionSubmit entities within the Code Champion platform.
 *
 * @author lenovo
 * @description Service for database operations on the question_submit table
 * @createDate 2025-03-05 14:41:41
 */
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /**
     * Processes a user's code submission for a question.
     * <p>
     * This method validates submission parameters, checks question existence,
     * verifies the programming language, and creates a submission record with
     * initial waiting status.
     *
     * @param questionSubmitAddRequest Request object containing question ID,
     *                                 programming language, and code content
     * @param loginUser                The authenticated user making the submission
     * @return The submission ID, which can be used to track submission status
     * @throws BusinessException if question doesn't exist, language is invalid,
     *                           or if there's a system error during submission
     */
    public long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    /**
     * Handles the internal transaction-protected submission process.
     * <p>
     * This method encapsulates the database operations required for processing
     * a submission with transaction support to ensure data integrity.
     *
     * @param userId The ID of the user making the submission
     * @param postId The ID of the question being submitted to
     * @return Integer indicating the operation result (1: added, -1: removed, 0: no change)
     * @throws BusinessException if there's a system error during processing
     */
    int doQuestionSubmitInner(long userId, long postId);

    /**
     * Creates a database query wrapper with conditions based on the provided request parameters.
     * <p>
     * This method builds filter conditions for question submissions based on criteria
     * such as language, status, question ID, user ID, and sorting preferences.
     *
     * @param questionQueryRequest The request containing search and filter parameters
     * @return A configured QueryWrapper object for database querying
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionQueryRequest);

    /**
     * Converts a QuestionSubmit entity to its VO representation with appropriate access control.
     * <p>
     * This method handles data transformation for presentation and applies security rules,
     * such as hiding code content from users who didn't create the submission.
     *
     * @param question  The QuestionSubmit entity to convert
     * @param loginUser The current authenticated user (for permission checking)
     * @return A QuestionSubmitVO object with properly secured data
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit question, User loginUser);

    /**
     * Converts a page of QuestionSubmit entities to a page of corresponding VOs.
     * <p>
     * This method transforms each submission in a paginated result set to its VO form,
     * applying security rules and maintaining pagination metadata.
     *
     * @param questionSubmitPage The page of QuestionSubmit entities from the database
     * @param loginUser          The current authenticated user (for permission checking)
     * @return A Page containing QuestionSubmitVO objects with appropriate access controls
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);
}
