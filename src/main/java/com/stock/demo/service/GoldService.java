package com.stock.demo.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.stock.demo.pojo.Gold;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/23
 * Time: 9:54
 * Description:
 */
@Service
public interface GoldService extends BaseService<Gold>{
    public Gold selectByWrapperReturnBean(Wrapper<Gold> wrapper);
}
