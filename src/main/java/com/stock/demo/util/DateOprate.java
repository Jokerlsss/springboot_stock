package com.stock.demo.util;

import com.alibaba.fastjson.serializer.CalendarCodec;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/27
 * Time: 12:01
 * Description: About oprate of date
 */
public class DateOprate {
    /**
     * 判断是否为工作日
     * @param date (String)
     * @return boolean
     * @throws ParseException
     */
    public boolean isWorkDay(Date date) throws ParseException {
        boolean workDay=true;
        boolean weekDay=false;

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
//        cal.add(cal.DAY_OF_YEAR,2);
        // 判断是否为工作日：当不等于周六且不等于周日时
        if(cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){
            System.out.println(cal.get(Calendar.DAY_OF_WEEK));
            return workDay;
        } else{
            System.out.println(cal.get(Calendar.DAY_OF_WEEK));
            return weekDay;
        }
    }

    /**
     * 判断日期是否为今天，是今天：返回true，不是今天：返回false
     * @param date
     * @return
     */
    public boolean isToday(String date){
//        Calendar calParam = Calendar.getInstance();
//        Calendar calToday = Calendar.getInstance();

//        calParam.setTime(date);
//        calParam.add(calParam.DAY_OF_YEAR,addDay) 增加一天
//        calToday.setTime(new Date());
        int addDay=1;

        // TODO: 把传进来的日期格式化之后，与当天日期进行比对
        // 返回 boolean 类型，让调用该方法的地方去进行循环，并加 1 天即可
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        // 获取当前日期，格式化如：2020-01-01
        String todayDate = formatter.format(new Date());
//        String paramDate=formatter.format(date);
        // 是今天的话，则停止生成，故返回false，反之亦然
        if(todayDate.equals(date)){
            System.out.println(date+"是今天");
            return true;
        }else{
            System.out.println(date+"不是今天");
            return false;
        }

//        System.out.println("今天日期："+todayDate);
//        System.out.println("传进来的日期："+paramDate);

//        System.out.println("总共加了天数："+addDay);
//
//        System.out.println(todayDate);
//        System.out.println(formatter.format(new Date()));
    }
}
