package com.stock.demo.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.stock.demo.pojo.PersonalFinancialAssets;
import org.springframework.security.core.parameters.P;
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
    // 根据 wrapper 查询
    public int selectByWrapperReturnInt(Wrapper<PersonalFinancialAssets> wrapper);
    // 根据 wrapper 查询返回 Bean
    public PersonalFinancialAssets selectByWrapperReturnBean(Wrapper<PersonalFinancialAssets> wrapper);
    // 根据 wrapper 查询返回 List
    public List<PersonalFinancialAssets> selectByWrapperReturnList(Wrapper<PersonalFinancialAssets> wrapper);
    // 根据 wrapper 更新 bean
    public int updateByWrapper(PersonalFinancialAssets personalFinancialAssets,Wrapper<PersonalFinancialAssets> wrapper);
}
