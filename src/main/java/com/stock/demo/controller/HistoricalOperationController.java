package com.stock.demo.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.pojo.HistoricalOperation;
import com.stock.demo.service.HistoricalOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/23
 * Time: 10:23
 * Description:
 */
@RestController
@RequestMapping(path={"/historicalOperation"})
public class HistoricalOperationController implements BaseController<HistoricalOperation>{

    @Autowired
    HistoricalOperationService historicalOperationService;

    /**
     * 查询个人操作记录
     * @param userid
     * @return
     */
    @GetMapping("listByWrapper")
    public List<HistoricalOperation> listByWrapper(@RequestParam(value = "userid", required=false ) Long userid){
        QueryWrapper<HistoricalOperation> wrapper=new QueryWrapper<>();
        wrapper.eq("userid",userid);
        return historicalOperationService.selectByWrapperReturnList(wrapper);
    }

    /**
     * 查询个人操作记录：倒序
     * @param userid
     * @return
     */
    @GetMapping("listForPersonal")
    public List<HistoricalOperation> listForPersonal(@RequestParam(value = "userid", required=false ) Long userid){
        QueryWrapper<HistoricalOperation> wrapper = new QueryWrapper<>();
        wrapper.eq("userid",userid);
        // 根据日期排倒序
        wrapper.orderByDesc("operatingdate");
        return historicalOperationService.selectByWrapperReturnList(wrapper);
    }

    @Override
    public List<HistoricalOperation> list() {
        return null;
    }

    /**
     * 添加操作记录（可以直接通过service来获取）
     * @param bean
     * @return
     */
    @Override
    @PostMapping("insert")
    public int insert(@RequestBody(required = false) HistoricalOperation bean) {
        return 0;
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public int update(HistoricalOperation bean) {
        return 0;
    }

    @Override
    public HistoricalOperation load(Long id) {
        return null;
    }

    @Override
    public Integer count() {
        return null;
    }

    @Override
    public IPage<HistoricalOperation> pager(Long pageNum, Long pageSize) {
        return null;
    }

    @Override
    public HistoricalOperation loadByName(String name) {
        return null;
    }

    @Override
    public IPage<HistoricalOperation> pagerByName(Wrapper<HistoricalOperation> wrapper, Long pageNum, Long pageSize) {
        return null;
    }
}
