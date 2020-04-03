package com.stock.demo.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.pojo.PersonalFinancialAssetsVO;
import com.stock.demo.service.PersonalFinancialAssetsVOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/4/3
 * Time: 14:29
 * Description:
 */
@RestController
@RequestMapping(path={"/personalFinancialAssetsVO"})
public class PersonalFinancialAssetsVOController implements BaseController<PersonalFinancialAssetsVO> {

    @Autowired
    PersonalFinancialAssetsVOService personalFinancialAssetsVOService;

    @GetMapping("listByWrapper")
    public List<PersonalFinancialAssetsVO> listByWrapper(@RequestParam(value = "userid", required=false ) String userid){
        return personalFinancialAssetsVOService.selectByWrapper(userid);
    }

    @Override
    public List<PersonalFinancialAssetsVO> list() {
        return null;
    }

    @Override
    public int insert(PersonalFinancialAssetsVO bean) throws ParseException {
        return 0;
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public int update(PersonalFinancialAssetsVO bean) {
        return 0;
    }

    @Override
    public PersonalFinancialAssetsVO load(Long id) {
        return null;
    }

    @Override
    public Integer count() {
        return null;
    }

    @Override
    public IPage<PersonalFinancialAssetsVO> pager(Long pageNum, Long pageSize) {
        return null;
    }

    @Override
    public PersonalFinancialAssetsVO loadByName(String name) {
        return null;
    }

    @Override
    public IPage<PersonalFinancialAssetsVO> pagerByName(Wrapper<PersonalFinancialAssetsVO> wrapper, Long pageNum, Long pageSize) {
        return null;
    }
}
