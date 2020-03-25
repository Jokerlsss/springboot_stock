package com.stock.demo.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.pojo.Fund;
import com.stock.demo.service.FundService;
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
 * Time: 10:22
 * Description: 基金
 */
@RestController
@RequestMapping(path={"/fund"})
public class FundController implements BaseController<Fund>{

    @Autowired
    FundService fundService;

    @Override
    public List<Fund> list() {
        return null;
    }

    /**
     * 后台系统新增基金
     * @param bean
     * @return
     */
    @Override
    @PostMapping("/insert")
    public int insert(@RequestBody(required = false) Fund bean) {
        return fundService.insert(bean);
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public int update(Fund bean) {
        return 0;
    }

    @Override
    public Fund load(Long id) {
        return null;
    }

    @Override
    public Integer count() {
        return null;
    }

    @Override
    public IPage<Fund> pager(Long pageNum, Long pageSize) {
        return null;
    }

    @Override
    public Fund loadByName(String name) {
        return null;
    }

    @Override
    public IPage<Fund> pagerByName(Wrapper<Fund> wrapper, Long pageNum, Long pageSize) {
        return null;
    }
}
