package com.stock.demo.service;

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
    public List<PersonalFinancialAssets> selectUserHoldProduct(Long userID);
}
