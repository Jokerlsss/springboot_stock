package com.stock.demo.common;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by linziyu on 2019/2/9.
 *
 * 用户认证成功处理类
 */

@Component("UserLoginAuthenticationSuccessHandler")
@Slf4j
public class UserLoginAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException,
            ServletException {

        System.out.println("进入success");
        JsonData jsonData = new JsonData();
        jsonData.setCode(20000);
        Map<String,String> data=new HashMap<>();
//        request.getParameter("username");
        /*判断每个人的职位并传不同的权限*/
//        if(request.getParameter("username").equals("wcg")){
//            data.put("token","editor-token");
//        }else if(request.getParameter("username").equals("ls")){
//            data.put("token","visitor-token");
//        }else{
//            data.put("token","admin-token");
//        }

        data.put("token","admin-token");

        jsonData.setData(data);
        String json = new Gson().toJson(jsonData);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write("json:"+json);
        out.flush();
        out.close();
        System.out.println("接入成功..");
    }
}

