package com.stock.demo.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.pojo.Stock;
import com.stock.demo.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/24
 * Time: 10:04
 * Description: 股票
 */
@RestController
@RequestMapping(path={"/stock"})
public class StockController implements BaseController<Stock>{

    @Autowired
    StockService stockService;

    @Override
    public List<Stock> list() {
        return null;
    }

    /**
     * 后台系统增加股票项目
     * @param bean
     * @return
     */
    @Override
    @PostMapping("/insert")
    public int insert(@RequestBody(required = false) Stock bean) {
        return stockService.insert(bean);
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public int update(Stock bean) {
        return 0;
    }

    @Override
    public Stock load(Long id) {
        return null;
    }

    @Override
    public Integer count() {
        return null;
    }

    @Override
    public IPage<Stock> pager(Long pageNum, Long pageSize) {
        return null;
    }

    @Override
    public Stock loadByName(String name) {
        return null;
    }

    @Override
    public IPage<Stock> pagerByName(Wrapper<Stock> wrapper, Long pageNum, Long pageSize) {
        return null;
    }
}
