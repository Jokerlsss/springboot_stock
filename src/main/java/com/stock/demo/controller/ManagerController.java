package com.stock.demo.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.pojo.Manager;
import com.stock.demo.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/23
 * Time: 10:23
 * Description:
 */
@RestController
@RequestMapping(path={"/manager"})
public class ManagerController implements BaseController<Manager>{

    @Autowired
    ManagerService managerService;

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
