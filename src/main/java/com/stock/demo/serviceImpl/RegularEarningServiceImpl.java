package com.stock.demo.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.mapper.RegularEarningsMapper;
import com.stock.demo.pojo.RegularEarnings;
import com.stock.demo.service.RegularEarningsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/23
 * Time: 10:09
 * Description:
 */
@Service
public class RegularEarningServiceImpl implements RegularEarningsService {

    @Autowired
    RegularEarningsMapper regularEarningsMapper;

    @Override
    public List<RegularEarnings> list() {
        return null;
    }

    @Override
    public int insert(RegularEarnings bean) {
        return regularEarningsMapper.insert(bean);
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public int update(RegularEarnings bean) {
        return 0;
    }

    @Override
    public RegularEarnings load(Long id) {
        return null;
    }

    @Override
    public Integer count() {
        return null;
    }

    @Override
    public IPage<RegularEarnings> pager(Long pageNum, Long pageSize) {
        return null;
    }

    @Override
    public RegularEarnings loadByName(String name) {
        return null;
    }

    @Override
    public IPage<RegularEarnings> pagerByName(Wrapper<RegularEarnings> wrapper, Long pageNum, Long pageSize) {
        return null;
    }

    @Override
    public RegularEarnings selectByWrapperReturnBean(Wrapper<RegularEarnings> wrapper) {
        return regularEarningsMapper.selectOne(wrapper);
    }
}
