package com.stock.demo.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.stock.demo.pojo.Fund;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/23
 * Time: 9:54
 * Description:
 */
@Service
public interface FundService extends BaseService<Fund>{
    public Fund selectByWrapperReturnBean(Wrapper<Fund> wrapper);
}
