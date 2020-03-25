package com.stock.demo.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.pojo.PersonalCollection;
import com.stock.demo.service.PersonalCollectionService;
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
@RequestMapping(path={"/personalCollection"})
public class PersonalCollectionController implements BaseController<PersonalCollection>{

    @Autowired
    PersonalCollectionService personalCollectionService;

    @Override
    public List<PersonalCollection> list() {
        return null;
    }

    @Override
    public int insert(PersonalCollection bean) {
        return 0;
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public int update(PersonalCollection bean) {
        return 0;
    }

    @Override
    public PersonalCollection load(Long id) {
        return null;
    }

    @Override
    public Integer count() {
        return null;
    }

    @Override
    public IPage<PersonalCollection> pager(Long pageNum, Long pageSize) {
        return null;
    }

    @Override
    public PersonalCollection loadByName(String name) {
        return null;
    }

    @Override
    public IPage<PersonalCollection> pagerByName(Wrapper<PersonalCollection> wrapper, Long pageNum, Long pageSize) {
        return null;
    }
}
