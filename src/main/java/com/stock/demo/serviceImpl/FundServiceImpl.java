package com.stock.demo.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.mapper.FundMapper;
import com.stock.demo.pojo.Fund;
import com.stock.demo.service.FundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/23
 * Time: 10:06
 * Description:
 */
@Service
public class FundServiceImpl implements FundService {

    @Autowired
    FundMapper fundMapper;

    @Override
    public List<Fund> list() {
        return null;
    }

    @Override
    public int insert(Fund bean) {
        return fundMapper.insert(bean);
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public int update(Fund bean) {
        return 0;
    }

    @Override
    public Fund load(Long id) {
        return null;
    }

    @Override
    public Integer count() {
        return null;
    }

    @Override
    public IPage<Fund> pager(Long pageNum, Long pageSize) {
        return null;
    }

    @Override
    public Fund loadByName(String name) {
        return null;
    }

    @Override
    public IPage<Fund> pagerByName(Wrapper<Fund> wrapper, Long pageNum, Long pageSize) {
        return null;
    }

    @Override
    public Fund selectByWrapperReturnBean(Wrapper<Fund> wrapper) {
        return fundMapper.selectOne(wrapper);
    }
}
