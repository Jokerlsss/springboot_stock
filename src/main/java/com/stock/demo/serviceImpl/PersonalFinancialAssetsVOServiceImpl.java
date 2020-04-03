package com.stock.demo.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.mapper.PersonalFinancialAssetsVOMapper;
import com.stock.demo.pojo.PersonalFinancialAssetsVO;
import com.stock.demo.service.PersonalFinancialAssetsVOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/4/3
 * Time: 14:29
 * Description:
 */
@Service
public class PersonalFinancialAssetsVOServiceImpl implements PersonalFinancialAssetsVOService {

    @Autowired
    PersonalFinancialAssetsVOMapper personalFinancialAssetsVOMapper;

    @Override
    public List<PersonalFinancialAssetsVO> list() {
        return null;
    }

    @Override
    public int insert(PersonalFinancialAssetsVO bean) {
        return 0;
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public int update(PersonalFinancialAssetsVO bean) {
        return 0;
    }

    @Override
    public PersonalFinancialAssetsVO load(Long id) {
        return null;
    }

    @Override
    public Integer count() {
        return null;
    }

    @Override
    public IPage<PersonalFinancialAssetsVO> pager(Long pageNum, Long pageSize) {
        return null;
    }

    @Override
    public PersonalFinancialAssetsVO loadByName(String name) {
        return null;
    }

    @Override
    public IPage<PersonalFinancialAssetsVO> pagerByName(Wrapper<PersonalFinancialAssetsVO> wrapper, Long pageNum, Long pageSize) {
        return null;
    }

    @Override
    public List<PersonalFinancialAssetsVO> selectByWrapper(String userid) {
        return personalFinancialAssetsVOMapper.selectByWrapper(userid);
    }
}
