package com.stock.demo.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.mapper.QuestionMapper;
import com.stock.demo.pojo.Question;
import com.stock.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/4/20
 * Time: 10:34
 * Description:
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionMapper questionMapper;

    @Override
    public List<Question> list() {
        return null;
    }

    @Override
    public int insert(Question bean) {
        return 0;
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public int update(Question bean) {
        return 0;
    }

    @Override
    public Question load(Long id) {
        return null;
    }

    @Override
    public Integer count() {
        return null;
    }

    @Override
    public IPage<Question> pager(Long pageNum, Long pageSize) {
        return null;
    }

    @Override
    public Question loadByName(String name) {
        return null;
    }

    @Override
    public IPage<Question> pagerByName(Wrapper<Question> wrapper, Long pageNum, Long pageSize) {
        return null;
    }

    @Override
    public List<Question> listByWrapper(QueryWrapper<Question> queryWrapper) {
        return questionMapper.selectList(queryWrapper);
    }

    @Override
    public Question beanByWrapper(QueryWrapper<Question> queryWrapper) {
        return questionMapper.selectOne(queryWrapper);
    }
}
