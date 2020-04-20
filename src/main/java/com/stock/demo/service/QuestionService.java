package com.stock.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stock.demo.pojo.Question;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/4/20
 * Time: 10:33
 * Description:
 */
@Service
public interface QuestionService extends BaseService<Question>{
    /** 根据 wrapper 查询 List */
    public List<Question> listByWrapper(QueryWrapper<Question> queryWrapper);

    /** 根据 wrapper 查询 Bean */
    public Question beanByWrapper(QueryWrapper<Question> queryWrapper);
}
