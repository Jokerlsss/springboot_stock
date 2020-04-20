package com.stock.demo.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/4/20
 * Time: 10:28
 * Description:
 */
@TableName("answer")
@Data
public class Answer {
    public Answer(){}

    // 问题ID
    @TableField(value = "questionid")
    private int questionid;

    // 问题ID
    @TableField("answercontent")
    private String answercontent;

    // 答案ID
    @TableId(value = "answerid")
    private String answerid;

    // 分数
    @TableField("grade")
    private int grade;
}
