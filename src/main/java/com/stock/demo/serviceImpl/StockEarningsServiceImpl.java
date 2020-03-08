package com.stock.demo.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.pojo.StockEarnings;
import com.stock.demo.service.StockEarningsService;
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
public class StockEarningsServiceImpl implements StockEarningsService {
    @Override
    public List<StockEarnings> list() {
        return null;
    }

    @Override
    public int insert(StockEarnings bean) {
        return 0;
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
