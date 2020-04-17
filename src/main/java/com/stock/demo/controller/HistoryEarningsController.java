package com.stock.demo.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.pojo.HistoryEarnings;

import java.text.ParseException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/4/16
 * Time: 22:09
 * Description:
 */
public class HistoryEarningsController implements BaseController<HistoryEarnings> {
    @Override
    public List<HistoryEarnings> list() {
        return null;
    }

    @Override
    public int insert(HistoryEarnings bean) throws ParseException {
        return 0;
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public int update(HistoryEarnings bean) {
        return 0;
    }

    @Override
    public HistoryEarnings load(Long id) {
        return null;
    }

    @Override
    public Integer count() {
        return null;
    }

    @Override
    public IPage<HistoryEarnings> pager(Long pageNum, Long pageSize) {
        return null;
    }

    @Override
    public HistoryEarnings loadByName(String name) {
        return null;
    }

    @Override
    public IPage<HistoryEarnings> pagerByName(Wrapper<HistoryEarnings> wrapper, Long pageNum, Long pageSize) {
        return null;
    }
}
