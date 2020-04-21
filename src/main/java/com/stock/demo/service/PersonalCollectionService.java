package com.stock.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stock.demo.pojo.PersonalCollection;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/23
 * Time: 9:55
 * Description:
 */
@Service
public interface PersonalCollectionService extends BaseService<PersonalCollection>{
    /** 根据 wrapper 查询个人收藏 */
    public List<PersonalCollection> listByWrapper(QueryWrapper<PersonalCollection> wrapper);

    /** 根据 wrapper 删除个人收藏 */
    public int deleteByWrapper(QueryWrapper<PersonalCollection> wrapper);
}
