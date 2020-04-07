package com.stock.demo.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.pojo.StockEarnings;
import com.stock.demo.service.StockEarningsService;
import com.stock.demo.service.StockService;
import org.bouncycastle.asn1.sec.SECNamedCurves;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/24
 * Time: 10:04
 * Description:
 */
@RestController
@RequestMapping(path={"/stockEarnings"})
public class StockEarningsController implements BaseController<StockEarnings>{

    @Autowired
    StockEarningsService stockEarningsService;

    @Override
    public List<StockEarnings> list() {
        return null;
    }

    @Override
    public int insert(StockEarnings bean) {
        return stockEarningsService.insert(bean);
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public int update(StockEarnings bean) {
        return 0;
    }

    @Override
    public StockEarnings load(Long id) {
        return null;
    }

    @Override
    public Integer count() {
        return null;
    }

    @Override
    public IPage<StockEarnings> pager(Long pageNum, Long pageSize) {
        return null;
    }

    @Override
    public StockEarnings loadByName(String name) {
        return null;
    }

    @Override
    public IPage<StockEarnings> pagerByName(Wrapper<StockEarnings> wrapper, Long pageNum, Long pageSize) {
        return null;
    }
}
