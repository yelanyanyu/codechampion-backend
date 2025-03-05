package com.yelanyanyu.codechampion.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yelanyanyu.codechampion.exception.BusinessException;
import com.yelanyanyu.codechampion.model.dto.question.QuestionQueryRequest;
import com.yelanyanyu.codechampion.model.entity.Question;
import com.yelanyanyu.codechampion.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
 * Service interface for managing Question entities in the system.
 * <p>
 * This interface provides methods for question validation, query construction,
 * and conversion between Question entities and their view representations.
 * It extends the MyBatis-Plus IService to inherit basic CRUD operations.
 *
 * @author lenovo
 * @description 针对表【question(题目)】的数据库操作Service
 * @createDate 2025-03-05 14:41:41
 * @see com.yelanyanyu.codechampion.model.entity.Question
 * @see com.baomidou.mybatisplus.extension.service.IService
 */
public interface QuestionService extends IService<Question> {

    /**
     * Validates the submitted question data to ensure it is legal.
     * This method checks various fields of the `Question` object and throws
     * a `BusinessException` if any validation fails.
     *
     * @param question The `Question` object containing the data to be validated.
     * @param add      A boolean flag indicating whether the validation is for adding a new question.
     *                 If true, certain fields must not be blank.
     * @throws BusinessException if the validation fails.
     */
    void validQuestion(Question question, boolean add);

    /**
     * Constructs a QueryWrapper for Question entities based on the provided query request parameters.
     * This method builds a dynamic SQL query with various conditions for filtering questions.
     *
     * <p>The method applies the following filters if the corresponding fields are provided:
     * <ul>
     *   <li>ID - exact match</li>
     *   <li>Title - partial match (LIKE)</li>
     *   <li>Content - partial match (LIKE)</li>
     *   <li>Tags - searches for tags in JSON format</li>
     *   <li>Answer - searches in content column</li>
     *   <li>User ID - exact match</li>
     * </ul>
     *
     * <p>The results can be sorted based on the provided sort field and order.
     *
     * @param questionQueryRequest The request object containing query parameters; can be null
     * @return A configured QueryWrapper object to be used in database queries
     */
    QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest);

    /**
     * Converts a Question entity to a QuestionVO (View Object) and enriches it with user information.
     * This method fetches the associated user data and adds it to the question view object.
     *
     * @param question The Question entity to be converted
     * @param request  HTTP request object (not used in current implementation)
     * @return An enriched QuestionVO containing both question and associated user data
     */
    QuestionVO getQuestionVO(Question question, HttpServletRequest request);

    /**
     * Converts a page of Question entities to a page of QuestionVO objects with associated user information.
     * This method performs batch queries to efficiently retrieve all associated users in a single database call,
     * then maps the questions to QuestionVOs with their corresponding user information.
     * <p>
     * The implementation optimizes performance by:
     * 1. Collecting all unique user IDs from the questions
     * 2. Fetching all users in one batch query
     * 3. Grouping users by their ID for efficient lookup during mapping
     *
     * @param questionPage Page containing Question entities to be converted
     * @param request      HTTP request object (not used in current implementation)
     * @return A page of QuestionVO objects with the same pagination parameters as the input
     */
    Page<QuestionVO> getQuestionVOPage(Page<Question> questionPage, HttpServletRequest request);

}
