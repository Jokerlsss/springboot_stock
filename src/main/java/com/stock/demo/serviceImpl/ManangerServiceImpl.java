package com.stock.demo.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.mapper.ManagerMapper;
import com.stock.demo.pojo.Manager;
import com.stock.demo.service.ManagerService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/23
 * Time: 10:09
 * Description:
 */
@Service
public class ManangerServiceImpl implements ManagerService {

    @Autowired
    ManagerMapper managerMapper;

    @Override
    public List<Manager> list() {
        return null;
    }

    @Override
    public int insert(Manager bean) {
        return 0;
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public int update(Manager bean) {
        return 0;
    }

    @Override
    public Manager load(Long id) {
        return null;
    }

    @Override
    public Integer count() {
        return null;
    }

    @Override
    public IPage<Manager> pager(Long pageNum, Long pageSize) {
        return null;
    }

    @Override
    public Manager loadByName(String name) {
        return null;
    }

    @Override
    public IPage<Manager> pagerByName(Wrapper<Manager> wrapper, Long pageNum, Long pageSize) {
        return null;
    }
}
