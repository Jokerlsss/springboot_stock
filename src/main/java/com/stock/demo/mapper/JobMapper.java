package com.stock.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stock.demo.pojo.Job;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/6
 * Time: 11:58
 * Description:
 */
@Mapper
public interface JobMapper extends BaseMapper<Job> {
}
