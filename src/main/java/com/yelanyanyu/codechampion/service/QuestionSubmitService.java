package com.yelanyanyu.codechampion.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
 * @author lenovo
 * @description 针对表【question_submit(题目提交)】的数据库操作Service
 * @createDate 2025-03-05 14:41:41
 */
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /**
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return 题目提交 id，方便用户随时查看题目提交状态
     */
    public long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    /**
     *
     *
     * @param userId
     * @param postId
     * @return
     */
    int doQuestionSubmitInner(long userId, long postId);

    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionQueryRequest);

    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit question, User loginUser);

    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);
}
