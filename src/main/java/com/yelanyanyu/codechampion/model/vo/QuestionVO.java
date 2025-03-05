package com.yelanyanyu.codechampion.model.vo;

import cn.hutool.json.JSONUtil;
import com.yelanyanyu.codechampion.model.dto.question.JudgeConfig;
import com.yelanyanyu.codechampion.model.entity.Question;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Question View Object (VO) for transferring question data to the presentation layer.
 * <p>
 * This class provides a data structure optimized for the view layer, with converted and
 * formatted data from the Question entity. It handles JSON serialization/deserialization
 * of complex fields like tags and judgeConfig, and includes utility methods for converting
 * between VO and entity objects.
 *
 * @author yelanyanyu
 */
@Data
public class QuestionVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Unique identifier of the question
     */
    private Long id;

    /**
     * Title of the question
     */
    private String title;

    /**
     * Content/description of the question
     */
    private String content;

    /**
     * List of tags associated with the question (deserialized from JSON array)
     */
    private List<String> tags;

    /**
     * Total number of submissions for this question
     */
    private Integer submitNum;

    /**
     * Number of accepted/correct submissions for this question
     */
    private Integer acceptedNum;

    /**
     * Configuration for the judge system (deserialized from JSON object)
     */
    private JudgeConfig judgeConfig;

    /**
     * Number of likes/thumbs up the question has received
     */
    private Integer thumbNum;

    /**
     * Number of times the question has been favorited/bookmarked
     */
    private Integer favourNum;

    /**
     * ID of the user who created this question
     */
    private Long userId;

    /**
     * Timestamp when the question was created
     */
    private Date createTime;

    /**
     * Timestamp when the question was last updated
     */
    private Date updateTime;

    /**
     * View object containing information about the question creator
     */
    private UserVO userVO;

    /**
     * Converts a QuestionVO object to a Question entity
     * <p>
     * This method handles the conversion of view-layer specific formats (like List<String>)
     * back to storage formats (like JSON strings) needed by the Question entity.
     *
     * @param questionVO The QuestionVO object to convert
     * @return A Question entity populated with data from the VO, or null if input is null
     */
    public static Question voToObj(QuestionVO questionVO) {
        if (questionVO == null) {
            return null;
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionVO, question);
        List<String> tagList = questionVO.getTags();
        question.setTags(JSONUtil.toJsonStr(tagList));
        JudgeConfig voJudgeConfig = questionVO.getJudgeConfig();
        if (voJudgeConfig != null) {
            question.setJudgeConfig(JSONUtil.toJsonStr(voJudgeConfig));
        }
        return question;
    }

    /**
     * Converts a Question entity to a QuestionVO object
     * <p>
     * This method handles the conversion of storage formats (like JSON strings)
     * to view-layer specific formats (like List<String> or deserialized objects).
     *
     * @param question The Question entity to convert
     * @return A QuestionVO populated with data from the entity, or null if input is null
     */
    public static QuestionVO objToVo(Question question) {
        if (question == null) {
            return null;
        }
        QuestionVO questionVO = new QuestionVO();
        BeanUtils.copyProperties(question, questionVO);
        questionVO.setTags(JSONUtil.toList(question.getTags(), String.class));

        String questionJudgeConfig = question.getJudgeConfig();
        if (questionJudgeConfig != null) {
            questionVO.setJudgeConfig(JSONUtil.toBean(questionJudgeConfig, JudgeConfig.class));
        }
        return questionVO;
    }
}
