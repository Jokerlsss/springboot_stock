package com.stock.demo.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.stock.demo.pojo.RegularEarnings;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/23
 * Time: 9:55
 * Description:
 */
@Service
public interface RegularEarningsService extends BaseService<RegularEarnings>{
    public RegularEarnings selectByWrapperReturnBean(Wrapper<RegularEarnings> wrapper);
}
