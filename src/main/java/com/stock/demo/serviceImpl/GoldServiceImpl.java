package com.stock.demo.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.mapper.GoldMapper;
import com.stock.demo.pojo.Gold;
import com.stock.demo.service.GoldService;
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
public class GoldServiceImpl implements GoldService {

    @Autowired
    GoldMapper goldMapper;

    @Override
    public List<Gold> list() {
        return null;
    }

    @Override
    public int insert(Gold bean) {
        return goldMapper.insert(bean);
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public int update(Gold bean) {
        return 0;
    }

    @Override
    public Gold load(Long id) {
        return null;
    }

    @Override
    public Integer count() {
        return null;
    }

    @Override
    public IPage<Gold> pager(Long pageNum, Long pageSize) {
        return null;
    }

    @Override
    public Gold loadByName(String name) {
        return null;
    }

    @Override
    public IPage<Gold> pagerByName(Wrapper<Gold> wrapper, Long pageNum, Long pageSize) {
        return null;
    }

    @Override
    public Gold selectByWrapperReturnBean(Wrapper<Gold> wrapper) {
        return goldMapper.selectOne(wrapper);
    }
}
