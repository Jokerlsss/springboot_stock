package com.stock.demo.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.mapper.RegularMapper;
import com.stock.demo.pojo.Regular;
import com.stock.demo.service.RegularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/23
 * Time: 10:09
 * Description:
 */
@Service
public class RegularServiceImpl implements RegularService {

    @Autowired
    RegularMapper regularMapper;

    @Override
    public List<Regular> list() {
        return null;
    }

    @Override
    public int insert(Regular bean) {
        return regularMapper.insert(bean);
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public int update(Regular bean) {
        return 0;
    }

    @Override
    public Regular load(Long id) {
        return null;
    }

    @Override
    public Integer count() {
        return null;
    }

    @Override
    public IPage<Regular> pager(Long pageNum, Long pageSize) {
        return null;
    }

    @Override
    public Regular loadByName(String name) {
        return null;
    }

    @Override
    public IPage<Regular> pagerByName(Wrapper<Regular> wrapper, Long pageNum, Long pageSize) {
        return null;
    }

    @Override
    public Regular selectByWrapperReturnBean(Wrapper<Regular> wrapper) {
        return regularMapper.selectOne(wrapper);
    }
}
