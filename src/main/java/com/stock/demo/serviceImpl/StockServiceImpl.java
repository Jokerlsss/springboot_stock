package com.stock.demo.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.pojo.Stock;
import com.stock.demo.service.StockService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/8
 * Time: 11:47
 * Description:
 */
@Service
public class StockServiceImpl implements StockService {
    @Override
    public List<Stock> list() {
        return null;
    }

    @Override
    public int insert(Stock bean) {
        return 0;
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
