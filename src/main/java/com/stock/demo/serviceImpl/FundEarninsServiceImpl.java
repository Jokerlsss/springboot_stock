package com.stock.demo.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.mapper.FundEarningsMapper;
import com.stock.demo.pojo.FundEarnings;
import com.stock.demo.service.FundEarningsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/23
 * Time: 10:08
 * Description:
 */
@Service
public class FundEarninsServiceImpl implements FundEarningsService {

    @Autowired
    FundEarningsMapper fundEarningsMapper;

    @Override
    public List<FundEarnings> list() {
        return null;
    }

    @Override
    public int insert(FundEarnings bean) {
        return 0;
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public int update(FundEarnings bean) {
        return 0;
    }

    @Override
    public FundEarnings load(Long id) {
        return null;
    }

    @Override
    public Integer count() {
        return null;
    }

    @Override
    public IPage<FundEarnings> pager(Long pageNum, Long pageSize) {
        return null;
    }

    @Override
    public FundEarnings loadByName(String name) {
        return null;
    }

    @Override
    public IPage<FundEarnings> pagerByName(Wrapper<FundEarnings> wrapper, Long pageNum, Long pageSize) {
        return null;
    }
}
