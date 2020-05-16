package com.stock.demo.common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stock.demo.util.UpdateEarn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * 定时器
 */

@Component
@EnableScheduling
public class ScheduledTasks {

    @Autowired
    private UpdateEarn updateEarn;

    /**
     * 当预约记录超过时间，自动更改为已取消
     */
    //秒-分-时
    @Scheduled(cron = "1 0 0 * * ?")
    public void BookOrderTask() throws ParseException {
        updateEarn.insertEarnings();
        System.out.println("预约记录定时器更新成功！");
    }
}
