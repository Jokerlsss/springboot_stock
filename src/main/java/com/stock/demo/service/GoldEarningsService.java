package com.stock.demo.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.stock.demo.pojo.GoldEarnings;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/23
 * Time: 9:54
 * Description:
 */
@Service
public interface GoldEarningsService extends BaseService<GoldEarnings>{
    public GoldEarnings selectByWrapperReturnBean(Wrapper<GoldEarnings> wrapper);
}
