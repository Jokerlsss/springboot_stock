package com.stock.demo.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.stock.demo.pojo.PersonalFinancialAssets;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/20
 * Time: 10:00
 * Description:
 */
@Service
public interface PersonalFinancialAssetsService extends BaseService<PersonalFinancialAssets>{
    // 查看用户拥有的资产
    public List<PersonalFinancialAssets> selectUserHoldProduct(Long userID);
    // 根据条件删除项目
    public int deleteByWrapper(Wrapper<PersonalFinancialAssets> wrapper);
}
