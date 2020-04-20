package com.stock.demo.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.mapper.AnswerMapper;
import com.stock.demo.pojo.Answer;
import com.stock.demo.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/4/20
 * Time: 10:35
 * Description:
 */
@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    AnswerMapper answerMapper;

    @Override
    public List<Answer> list() {
        return null;
    }

    @Override
    public int insert(Answer bean) {
        return 0;
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public int update(Answer bean) {
        return 0;
    }

    @Override
    public Answer load(Long id) {
        return null;
    }

    @Override
    public Integer count() {
        return null;
    }

    @Override
    public IPage<Answer> pager(Long pageNum, Long pageSize) {
        return null;
    }

    @Override
    public Answer loadByName(String name) {
        return null;
    }

    @Override
    public IPage<Answer> pagerByName(Wrapper<Answer> wrapper, Long pageNum, Long pageSize) {
        return null;
    }

    @Override
    public List<Answer> listByWrapper(QueryWrapper<Answer> queryWrapper) {
        return answerMapper.selectList(queryWrapper);
    }

    @Override
    public Answer beanByWrapper(QueryWrapper<Answer> queryWrapper) {
        return answerMapper.selectOne(queryWrapper);
    }
}
