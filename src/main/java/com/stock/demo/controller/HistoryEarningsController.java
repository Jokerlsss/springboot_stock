package com.stock.demo.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.pojo.HistoryEarnings;
import com.stock.demo.service.HistoryEarningsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/4/16
 * Time: 22:09
 * Description:
 */
@RestController
@RequestMapping(path={"/historyEarnings"})
public class HistoryEarningsController implements BaseController<HistoryEarnings> {

    @Autowired
    HistoryEarningsService historyEarningsService;

    @GetMapping("getHistoryEarn")
    public List<HistoryEarnings> listByWrapper(@RequestParam(value = "userid", required=false ) Long userid){
        QueryWrapper<HistoryEarnings> historyEarningsQueryWrapper=new QueryWrapper<>();
        historyEarningsQueryWrapper.eq("userid",userid);
        List<HistoryEarnings> historyEarningsList=historyEarningsService.listByWrapper(historyEarningsQueryWrapper);
        return historyEarningsList;
    }

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
