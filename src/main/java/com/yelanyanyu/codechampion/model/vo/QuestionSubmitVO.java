package com.yelanyanyu.codechampion.model.vo;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.yelanyanyu.codechampion.judge.codesandbox.model.JudgeInfo;
import com.yelanyanyu.codechampion.model.entity.QuestionSubmit;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * View Object (VO) for code submissions in the Code Champion platform.
 * <p>
 * This class represents the presentation-layer view of submission data, containing
 * both core submission details and related information (user, question) for display.
 * It handles JSON serialization/deserialization for complex objects like judgeInfo
 * and provides utility methods for conversion between entity and VO representations,
 * with appropriate data transformation and field mapping.
 * <p>
 * The VO pattern is used to:
 * - Transfer data between service layer and controllers
 * - Apply data security by selectively exposing fields
 * - Format and prepare data for frontend consumption
 *
 * @author yelanyanyu
 */
@Data
public class QuestionSubmitVO implements Serializable {
    /**
     * Serialization version ID for maintaining compatibility during class evolution
     */
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * Unique identifier for the submission record
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * Programming language used for the code submission
     * (e.g., "java", "python", "cpp")
     */
    private String language;

    /**
     * Submitted solution code content
     * Note: This field may be null when viewing submissions made by other users
     * due to access control in QuestionSubmitServiceImpl.getQuestionSubmitVO
     */
    private String code;

    /**
     * Judge information for the submission including execution metrics
     * and result details (memory usage, time consumed, status message)
     * Automatically converted from JSON string in the entity
     */
    private JudgeInfo judgeInfo;

    /**
     * Submission processing status
     * 0: Waiting to be judged
     * 1: Judging in progress
     * 2: Successfully judged
     * 3: Judge failed
     */
    private Integer status;

    /**
     * ID of the question this submission is for
     */
    private Long questionId;

    /**
     * ID of the user who made the submission
     */
    private Long userId;

    /**
     * Timestamp when the submission was created
     */
    private Date createTime;

    /**
     * Timestamp when the submission was last updated
     */
    private Date updateTime;

    /**
     * User information of the submitter
     * Populated separately by service layer when needed
     */
    private UserVO userVO;

    /**
     * Question information for the submission
     * Populated separately by service layer when needed
     */
    private QuestionVO questionVO;

    /**
     * Converts a QuestionSubmitVO to a QuestionSubmit entity.
     * <p>
     * This method performs the data transformation from view object to data model,
     * including serialization of the judgeInfo object to JSON string format
     * required by the entity.
     *
     * @param questionSubmitVO The view object to convert
     * @return A QuestionSubmit entity with properties copied from the VO,
     * or null if the input is null
     */
    public static QuestionSubmit voToObj(QuestionSubmitVO questionSubmitVO) {
        if (questionSubmitVO == null) {
            return null;
        }
        QuestionSubmit questionSubmit = new QuestionSubmit();
        BeanUtils.copyProperties(questionSubmitVO, questionSubmit);

        JudgeInfo vojudgeInfo = questionSubmitVO.getJudgeInfo();
        if (vojudgeInfo != null) {
            questionSubmit.setJudgeInfo(JSONUtil.toJsonStr(vojudgeInfo));
        }
        return questionSubmit;
    }

    /**
     * Converts a QuestionSubmit entity to a QuestionSubmitVO.
     * <p>
     * This method performs the data transformation from data model to view object,
     * including deserialization of the judgeInfo JSON string to an object.
     * Note that userVO and questionVO fields are not populated by this method
     * and must be set separately if needed.
     *
     * @param questionSubmit The entity to convert
     * @return A QuestionSubmitVO with properties copied from the entity,
     * or null if the input is null
     */
    public static QuestionSubmitVO objToVo(QuestionSubmit questionSubmit) {
        if (questionSubmit == null) {
            return null;
        }
        QuestionSubmitVO questionSubmitVO = new QuestionSubmitVO();
        BeanUtils.copyProperties(questionSubmit, questionSubmitVO);
        questionSubmitVO.setJudgeInfo(JSONUtil
                .toBean(questionSubmit.getJudgeInfo(), JudgeInfo.class));
        return questionSubmitVO;
    }
}
