package com.yelanyanyu.codechampion.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yelanyanyu.codechampion.mapper.QuestionMapper;
import com.yelanyanyu.codechampion.model.entity.Question;
import com.yelanyanyu.codechampion.service.QuestionService;
import org.springframework.stereotype.Service;

/**
* @author lenovo
* @description 针对表【question(题目)】的数据库操作Service实现
* @createDate 2025-03-05 14:41:41
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{

}




