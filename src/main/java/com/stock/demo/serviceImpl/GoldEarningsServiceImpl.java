package com.stock.demo.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.mapper.GoldEarningsMapper;
import com.stock.demo.pojo.GoldEarnings;
import com.stock.demo.service.GoldEarningsService;
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
public class GoldEarningsServiceImpl implements GoldEarningsService {

    @Autowired
    GoldEarningsMapper goldEarningsMapper;

    @Override
    public List<GoldEarnings> list() {
        return null;
    }

    @Override
    public int insert(GoldEarnings bean) {
        return goldEarningsMapper.insert(bean);
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

    @Override
    public GoldEarnings selectByWrapperReturnBean(Wrapper<GoldEarnings> wrapper) {
        return goldEarningsMapper.selectOne(wrapper);
    }
}
