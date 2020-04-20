package com.stock.demo.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.pojo.Answer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/4/20
 * Time: 10:36
 * Description:
 */
@RestController
@RequestMapping(path={"/answer"})
public class AnswerController implements BaseController<Answer> {
    @Override
    public List<Answer> list() {
        return null;
    }

    @Override
    public int insert(Answer bean) throws ParseException {
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
}
