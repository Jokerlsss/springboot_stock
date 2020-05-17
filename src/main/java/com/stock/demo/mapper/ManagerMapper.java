package com.stock.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stock.demo.pojo.Manager;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/23
 * Time: 9:41
 * Description:
 */
@Mapper
public interface ManagerMapper  extends BaseMapper<Manager> {
    @Select("select * from manager where managerName=#{managerName}")
    public Manager selectByName(String managerName);
}
