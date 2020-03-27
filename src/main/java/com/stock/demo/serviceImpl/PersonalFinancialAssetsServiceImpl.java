package com.stock.demo.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.mapper.PersonalFinancialAssetsMapper;
import com.stock.demo.pojo.PersonalFinancialAssets;
import com.stock.demo.service.PersonalFinancialAssetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/20
 * Time: 10:02
 * Description:
 */
@Service
public class PersonalFinancialAssetsServiceImpl implements PersonalFinancialAssetsService {

    @Autowired
    PersonalFinancialAssetsMapper personalFinancialAssetsMapper;

    @Override
    public List<PersonalFinancialAssets> list() {
        return null;
    }

    @Override
    public int insert(PersonalFinancialAssets bean) {
        return personalFinancialAssetsMapper.insert(bean);
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public int update(PersonalFinancialAssets bean) {
        return 0;
    }

    @Override
    public PersonalFinancialAssets load(Long id) {
        return null;
    }

    @Override
    public Integer count() {
        return null;
    }

    @Override
    public IPage<PersonalFinancialAssets> pager(Long pageNum, Long pageSize) {
        return null;
    }

    @Override
    public PersonalFinancialAssets loadByName(String name) {
        return null;
    }

    @Override
    public IPage<PersonalFinancialAssets> pagerByName(Wrapper<PersonalFinancialAssets> wrapper, Long pageNum, Long pageSize) {
        return null;
    }


    @Override
    public List<PersonalFinancialAssets> selectUserHoldProduct(Long userID) {
        return personalFinancialAssetsMapper.selectUserHoldProduct(userID);
    }

    /**
     * 根据条件删除
     * @param wrapper
     * @return
     */
    @Override
    public int deleteByWrapper(Wrapper<PersonalFinancialAssets> wrapper) {
        return personalFinancialAssetsMapper.delete(wrapper);
    }
}
