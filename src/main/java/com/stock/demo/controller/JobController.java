package com.stock.demo.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.pojo.Job;
import com.stock.demo.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/6
 * Time: 11:59
 * Description:
 */
@RestController
@RequestMapping(path={"/job"})
public class JobController implements BaseController<Job> {

    @Autowired
    private JobService jobService;

    @Override
    @GetMapping("list")
    public List<Job> list() {
        return jobService.list();
    }

    @Override
    public int insert(Job bean) {
        return 0;
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public int update(Job bean) {
        return 0;
    }

    @Override
    public Job load(Long id) {
        return null;
    }

    @Override
    public Integer count() {
        return null;
    }

    @Override
    public IPage<Job> pager(Long pageNum, Long pageSize) {
        return null;
    }

    @Override
    public Job loadByName(String name) {
        return null;
    }

    @Override
    public IPage<Job> pagerByName(Wrapper<Job> wrapper, Long pageNum, Long pageSize) {
        return null;
    }
}
