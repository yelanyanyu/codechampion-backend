package com.yelanyanyu.codechampion.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yelanyanyu.codechampion.common.BaseResponse;
import com.yelanyanyu.codechampion.common.ErrorCode;
import com.yelanyanyu.codechampion.common.ResultUtils;
import com.yelanyanyu.codechampion.exception.BusinessException;
import com.yelanyanyu.codechampion.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.yelanyanyu.codechampion.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.yelanyanyu.codechampion.model.entity.QuestionSubmit;
import com.yelanyanyu.codechampion.model.entity.User;
import com.yelanyanyu.codechampion.model.vo.QuestionSubmitVO;
import com.yelanyanyu.codechampion.service.QuestionSubmitService;
import com.yelanyanyu.codechampion.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * REST controller for managing code submissions to questions.
 * <p>
 * This controller provides endpoints for submitting solutions to coding problems
 * and retrieving paginated lists of submissions. Access to submission history
 * is controlled based on the user's role and relationship to the submission.
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/question_submit")
@Slf4j
public class QuestionSubmitController {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private UserService userService;

    /**
     * Processes a code submission for a question and initiates the judging process.
     * <p>
     * This endpoint accepts a submission for a specific question from an authenticated user,
     * validates the input parameters, and passes the submission to the judging system.
     * The response contains the ID of the created submission record.
     *
     * @param questionSubmitAddRequest The request body containing submission details including
     *                                 question ID, programming language, and code content
     * @param request                  The HTTP request object used to authenticate the current user
     * @return BaseResponse containing the submission ID if successful
     * @throws BusinessException if the request parameters are invalid or the user is not authenticated
     */
    @PostMapping("/")
    public BaseResponse<Long> doQuestionSubmit(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest,
                                               HttpServletRequest request) {
        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 登录才能点赞
        final User loginUser = userService.getLoginUser(request);
//        long questionId = questionSubmitAddRequest.getQuestionId();
        long result = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest, loginUser);
        return ResultUtils.success(result);
    }

    /**
     * Retrieves a paginated list of question submissions.
     * <p>
     * This endpoint provides access to submission records based on query parameters
     * and user permissions. The results are filtered according to the user's role:
     * - Admin users can see all submissions
     * - Regular users can only see their own submissions or public information about others' submissions
     *
     * @param questionSubmitQueryRequest The request body containing filter criteria and pagination parameters
     * @param request                    The HTTP request object used to authenticate the current user
     * @return BaseResponse containing a page of QuestionSubmitVO objects with submission details
     * @throws BusinessException if the user is not authenticated or lacks appropriate permissions
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<QuestionSubmitVO>> listQuestionSubmitByPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest,
                                                                         HttpServletRequest request) {
        long current = questionSubmitQueryRequest.getCurrent();
        long size = questionSubmitQueryRequest.getPageSize();
        User loginUser = userService.getLoginUser(request);
        // 从数据库中查询原始的题目提交分页信息
        Page<QuestionSubmit> questionSubmitPage = questionSubmitService.page(new Page<>(current, size),
                questionSubmitService.getQueryWrapper(questionSubmitQueryRequest));
        return ResultUtils.success(questionSubmitService.getQuestionSubmitVOPage(questionSubmitPage, loginUser));
    }

}
