package com.stock.demo.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.mapper.HistoryEarningsMapper;
import com.stock.demo.pojo.HistoryEarnings;
import com.stock.demo.service.HistoryEarningsService;
import com.stock.demo.util.SuggestionGet;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/4/16
 * Time: 22:09
 * Description:
 */
@RestController
@RequestMapping(path={"/historyEarnings"})
public class HistoryEarningsController implements BaseController<HistoryEarnings> {

    @Autowired
    HistoryEarningsService historyEarningsService;

    @Autowired
    HistoryEarningsMapper historyEarningsMapper;

    @Autowired
    SuggestionGet suggestionGet;


    /**
     * 获取月报信息
     * @param userid
     * @return
     */
    @GetMapping("getMonthReportOfEarn")
    public Map<String,Object> getMonthReportOfEarn(@RequestParam(value = "userid",required = false) Long userid){
        Map<String,Object> resultMap = new HashMap<String,Object>(10);

        /** 查看本月盈利次数 */
        int countOfProfit=historyEarningsMapper.selectCountOfProfit(userid);
        /** 查看本月亏损次数 */
        int countOfLoss=historyEarningsMapper.selectCountOfLoss(userid);
        /** 查看本月收益 */
        float monthOfEarnings=historyEarningsMapper.selectMonthOfEarnings(userid);
        /** 查看本月收益最多是哪天 */
        HistoryEarnings historyEarnings=historyEarningsMapper.selectMostEarnings(userid);
        // 日期
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        String earningsDate=df.format(historyEarnings.getEarningsdate());
        // 收益
        float dayEarn=historyEarnings.getDayEarn();
        /** 建议 */
        String earnSuggestion=suggestionGet.historyEarnReport(countOfLoss,countOfProfit);

        resultMap.put("countOfProfit",countOfProfit);
        resultMap.put("countOfLoss",countOfLoss);
        resultMap.put("monthOfEarnings",monthOfEarnings);
        resultMap.put("earningsDate",earningsDate);
        resultMap.put("dayEarn",dayEarn);
        resultMap.put("earnSuggestion",earnSuggestion);

        return resultMap;
    }

    /**
     * 获取历史收益
     * @param userid
     * @return
     */
    @GetMapping("getHistoryEarn")
    public List<HistoryEarnings> listByWrapper(@RequestParam(value = "userid", required=false ) Long userid){
        QueryWrapper<HistoryEarnings> historyEarningsQueryWrapper=new QueryWrapper<>();
        historyEarningsQueryWrapper.eq("userid",userid);
        historyEarningsQueryWrapper.orderByDesc("earningsdate");
        List<HistoryEarnings> historyEarningsList=historyEarningsService.listByWrapper(historyEarningsQueryWrapper);
        return historyEarningsList;
    }

    /**
     * 查近几月历史收益  0：本月  1：上月  2：前月
     * @param userid
     * @param month
     * @return
     */
    @GetMapping("getMonthOfEarn")
    public List<HistoryEarnings> selectMonthOfList(@RequestParam(value = "userid", required=false ) Long userid,
                                                   @RequestParam int month){
        return historyEarningsMapper.selectMonthOfList(userid,month);
    }

    @Override
    public List<HistoryEarnings> list() {
        return null;
    }

    @Override
    public int insert(HistoryEarnings bean) throws ParseException {
        return 0;
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public int update(HistoryEarnings bean) {
        return 0;
    }

    @Override
    public HistoryEarnings load(Long id) {
        return null;
    }

    @Override
    public Integer count() {
        return null;
    }

    @Override
    public IPage<HistoryEarnings> pager(Long pageNum, Long pageSize) {
        return null;
    }

    @Override
    public HistoryEarnings loadByName(String name) {
        return null;
    }

    @Override
    public IPage<HistoryEarnings> pagerByName(Wrapper<HistoryEarnings> wrapper, Long pageNum, Long pageSize) {
        return null;
    }
}
