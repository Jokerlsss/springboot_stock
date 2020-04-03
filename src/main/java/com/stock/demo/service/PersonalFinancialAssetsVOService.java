package com.stock.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stock.demo.pojo.PersonalFinancialAssetsVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/4/3
 * Time: 14:28
 * Description:
 */
@Service
public interface PersonalFinancialAssetsVOService extends BaseService<PersonalFinancialAssetsVO> {
    public List<PersonalFinancialAssetsVO> selectByWrapper(String userid);
}
