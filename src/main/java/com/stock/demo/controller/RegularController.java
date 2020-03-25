package com.stock.demo.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.pojo.Regular;
import com.stock.demo.service.RegularService;
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
 * Description: 定期
 */
@RestController
@RequestMapping(path={"/regular"})
public class RegularController implements BaseController<Regular>{

    @Autowired
    RegularService regularService;

    @Override
    public List<Regular> list() {
        return null;
    }

    /**
     * 后台系统新增定期
     * @param bean
     * @return
     */
    @Override
    @PostMapping("/insert")
    public int insert(@RequestBody(required = false) Regular bean) {
        return regularService.insert(bean);
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public int update(Regular bean) {
        return 0;
    }

    @Override
    public Regular load(Long id) {
        return null;
    }

    @Override
    public Integer count() {
        return null;
    }

    @Override
    public IPage<Regular> pager(Long pageNum, Long pageSize) {
        return null;
    }

    @Override
    public Regular loadByName(String name) {
        return null;
    }

    @Override
    public IPage<Regular> pagerByName(Wrapper<Regular> wrapper, Long pageNum, Long pageSize) {
        return null;
    }
}
