package com.stock.demo.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.pojo.Gold;
import com.stock.demo.service.GoldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/23
 * Time: 10:23
 * Description: 黄金
 */
@RestController
@RequestMapping(path={"/gold"})
public class GoldController implements BaseController<Gold>{

    @Autowired
    GoldService goldService;

    @Override
    public List<Gold> list() {
        return null;
    }

    /**
     * 后台系统新增黄金
     * @param bean
     * @return
     */
    @Override
    @PostMapping("/insert")
    public int insert(@RequestBody(required = false) Gold bean) {
        return goldService.insert(bean);
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public int update(Gold bean) {
        return 0;
    }

    @Override
    public Gold load(Long id) {
        return null;
    }

    @Override
    public Integer count() {
        return null;
    }

    @Override
    public IPage<Gold> pager(Long pageNum, Long pageSize) {
        return null;
    }

    @Override
    public Gold loadByName(String name) {
        return null;
    }

    @Override
    public IPage<Gold> pagerByName(Wrapper<Gold> wrapper, Long pageNum, Long pageSize) {
        return null;
    }
}
