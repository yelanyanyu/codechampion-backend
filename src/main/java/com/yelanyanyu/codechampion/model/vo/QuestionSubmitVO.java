package com.yelanyanyu.codechampion.model.vo;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.yelanyanyu.codechampion.common.PageRequest;
import com.yelanyanyu.codechampion.model.dto.question.JudgeConfig;
import com.yelanyanyu.codechampion.model.dto.questionsubmit.JudgeInfo;
import com.yelanyanyu.codechampion.model.entity.Question;
import com.yelanyanyu.codechampion.model.entity.QuestionSubmit;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
public class QuestionSubmitVO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 编程语言
     */
    private String language;
    /**
     * 用户代码
     */
    private String code;
    /**
     * 判题信息（json 对象）
     */
    private JudgeInfo judgeInfo;
    /**
     * 判题状态（0 - 待判题、1 - 判题中、2 - 成功、3 - 失败）
     */
    private Integer status;
    /**
     * 题目 id
     */
    private Long questionId;
    /**
     * 创建用户 id
     */
    private Long userId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    private UserVO userVO;
    private QuestionVO questionVO;

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
