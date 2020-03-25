package com.stock.demo.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.pojo.GoldEarnings;
import com.stock.demo.service.GoldEarningsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/23
 * Time: 10:23
 * Description:
 */
@RestController
@RequestMapping(path={"/goldEarnings"})
public class GoldEarningsController implements BaseController<GoldEarnings>{

    @Autowired
    GoldEarningsService goldEarningsService;

    @Override
    public List<GoldEarnings> list() {
        return null;
    }

    @Override
    public int insert(GoldEarnings bean) {
        return 0;
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public int update(GoldEarnings bean) {
        return 0;
    }

    @Override
    public GoldEarnings load(Long id) {
        return null;
    }

    @Override
    public Integer count() {
        return null;
    }

    @Override
    public IPage<GoldEarnings> pager(Long pageNum, Long pageSize) {
        return null;
    }

    @Override
    public GoldEarnings loadByName(String name) {
        return null;
    }

    @Override
    public IPage<GoldEarnings> pagerByName(Wrapper<GoldEarnings> wrapper, Long pageNum, Long pageSize) {
        return null;
    }
}
