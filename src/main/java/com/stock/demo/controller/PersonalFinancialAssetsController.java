package com.stock.demo.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.pojo.FinancialProduct;
import com.stock.demo.pojo.PersonalFinancialAssets;
import com.stock.demo.service.PersonalFinancialAssetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.jnlp.PersistenceService;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/20
 * Time: 10:02
 * Description:
 */
@RestController
@RequestMapping(path={"/personalFinancialAssets"})
public class PersonalFinancialAssetsController implements BaseController<PersonalFinancialAssets>{

    @Autowired
    PersonalFinancialAssetsService personalFinancialAssetsService;

    /**
     * 查询用户持有资产
     * @param userID
     * @return List<PersonalFinancialAssets>
     */
    @GetMapping("listById")
    public List<PersonalFinancialAssets> listById(@RequestParam(value="userID",required = false) Long userID){
        return personalFinancialAssetsService.selectUserHoldProduct(userID);
    }

    /**
     * 根据 userID、个人资产ID 删除
     * @param bean
     * @return
     */
    // TODO: 异常处理
    @PostMapping("deleteByWrapper")
    public int deleteByWrapper(@RequestBody(required = false) PersonalFinancialAssets bean){
        QueryWrapper<PersonalFinancialAssets> queryWrapper=new QueryWrapper<>();
        Long userID=bean.getUserID();
        Long PersonalFinancialAssetsID=bean.getPersonalFinancialAssetsID();
        queryWrapper.eq("personalFinancialAssetsID",PersonalFinancialAssetsID);
        queryWrapper.eq("userID",userID);
        System.out.println("deleteByWrapper");
        return personalFinancialAssetsService.deleteByWrapper(queryWrapper);
    }


    @Override
    public List<PersonalFinancialAssets> list() {
        return null;
    }

    @Override
    @PostMapping("insert")
    public int insert(@RequestBody(required = false) PersonalFinancialAssets bean) {
        return personalFinancialAssetsService.insert(bean);
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public int update(PersonalFinancialAssets bean) {
        return 0;
    }

    @Override
    public PersonalFinancialAssets load(Long id) {
        return null;
    }

    @Override
    public Integer count() {
        return null;
    }

    @Override
    public IPage<PersonalFinancialAssets> pager(Long pageNum, Long pageSize) {
        return null;
    }

    @Override
    public PersonalFinancialAssets loadByName(String name) {
        return null;
    }

    @Override
    public IPage<PersonalFinancialAssets> pagerByName(Wrapper<PersonalFinancialAssets> wrapper, Long pageNum, Long pageSize) {
        return null;
    }
}
