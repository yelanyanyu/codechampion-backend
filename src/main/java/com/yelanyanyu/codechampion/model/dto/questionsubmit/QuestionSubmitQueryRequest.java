package com.yelanyanyu.codechampion.model.dto.questionsubmit;


import com.baomidou.mybatisplus.annotation.TableField;
import com.yelanyanyu.codechampion.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author yelanyanyu@zjxu.edu.cn
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionSubmitQueryRequest extends PageRequest implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 编程语言
     */
    private String language;
    /**
     * 用户代码
     */
    private String status;
    /**
     * 题目 id
     */
    private Long questionId;
    private Long userId;

}
