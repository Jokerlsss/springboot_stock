package com.stock.demo.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.mapper.HistoricalOperationMapper;
import com.stock.demo.pojo.HistoricalOperation;
import com.stock.demo.service.HistoricalOperationService;
import com.stock.demo.util.SuggestionGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    HistoricalOperationMapper historicalOperationMapper;

    @Autowired
    SuggestionGet suggestionGet;

    /**
     * 获取月报中的操作信息
     * @param userid
     * @return
     */
    @GetMapping("getMonthReportOfOperate")
    public Map<String,Object> getMonthReportOfOperate(@RequestParam(value = "userid",required = false) Long userid){
        Map<String,Object> resultMap = new HashMap<String,Object>(10);
        // TODO 空异常处理

        /** 本月总操作次数 */
        int countOfOperate=historicalOperationMapper.selectCountOfOperate(userid);

        /** 本月买入次数 */
        int countOfBuy=historicalOperationMapper.selectCountOfBuy(userid);

        /** 本月卖出次数 */
        int countOfSell=historicalOperationMapper.selectCountOfSell(userid);

        /** 本月加仓次数 */
        int countOfAdd=historicalOperationMapper.selectCountOfAdd(userid);

        /** 本月操作金额最大的资产 */
        HistoricalOperation historicalOperation=historicalOperationMapper.selectMostOperateAmount(userid);

        /** 建议 */
        String operateSuggestion=suggestionGet.historyOperateReport(countOfOperate);

        resultMap.put("countOfOperate",countOfOperate);
        resultMap.put("countOfBuy",countOfBuy);
        resultMap.put("countOfSell",countOfSell);
        resultMap.put("countOfAdd",countOfAdd);
        resultMap.put("historicalOperation",historicalOperation);
        resultMap.put("operateSuggestion",operateSuggestion);

        return resultMap;
    }

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
     * 查近几月历史操作，0：本月 1：上月 2：前月
     * @param userid
     * @param month
     * @return
     */
    @GetMapping("getMonthOfOperate")
    public List<HistoricalOperation> getMonthOfOpeate(@RequestParam(value = "userid", required=false ) Long userid,
                                                      @RequestParam(value = "month", required=false ) int month){
        return historicalOperationMapper.selectMonthOfList(userid,month);
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
