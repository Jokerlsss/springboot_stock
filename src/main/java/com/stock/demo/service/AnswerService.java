package com.stock.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stock.demo.pojo.Answer;
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
public interface AnswerService extends BaseService<Answer>{
    /** 根据 wrapper 查询 List */
    public List<Answer> listByWrapper(QueryWrapper<Answer> queryWrapper);

    /** 根据 wrapper 查询 Bean */
    public Answer beanByWrapper(QueryWrapper<Answer> queryWrapper);
}
