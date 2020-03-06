package com.stock.demo.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/2/9
 * Time: 22:00
 * Description:
 */
public interface BaseService<T> {
    List<T> list();
    int insert(T bean);
    int delete(Long id);
    int update(T bean);
    T load(Long id);
    Integer count();
    IPage<T> pager(Long pageNum, Long pageSize);
    T loadByName(String name);
    IPage<T> pagerByName(Wrapper<T> wrapper, Long pageNum, Long pageSize);
}
