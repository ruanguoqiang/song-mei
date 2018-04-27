package com.xuezhijian.controller;

import com.alibaba.fastjson.JSON;
import com.xuezhijian.model.Barrager;
import com.xuezhijian.service.BarragerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.Map;

@Controller
public class BarragerControl {
    @Autowired
    BarragerService barragerService;
    @RequestMapping(value = "/getBarrager")
    @ResponseBody
    public String getBarragerJson(){
      Map<String,Barrager> allBarragerMap =barragerService.getAllBarrager();
        String barragerJson= JSON.toJSONString(allBarragerMap);
        return barragerJson;
    }
    @RequestMapping(value = "/getActualBarrager")
    @ResponseBody
    public String getActualBarraJson(){
        Map<String,Barrager> actualBarragerMap= barragerService.getActualBarrager();
        if (actualBarragerMap!=null) {
            String actualBarragerJson = JSON.toJSONString(actualBarragerMap);
            return actualBarragerJson;
        }
        return null;
    }

    @RequestMapping(value = "/addBarrager",method = RequestMethod.POST)
    @ResponseBody
    public String addbarrager(@RequestBody String info, HttpServletRequest httpServletRequest) throws Exception{
        String username=(String)httpServletRequest.getSession().getAttribute("name");
        info= URLDecoder.decode(info,"UTF-8");
        Barrager barrager=new Barrager();
      /*  int barid=(int)(Math.random()*9000+1000);
        barrager.setBarrid(barid);*/
        barrager.setInfo(info.substring(3));
        String href="http://47.104.211.21:0501/myself?username="+username;
        barrager.setHref(href);
        barrager.setBottom((int)(Math.random() * 600));
        double speed=8+Math.random()*10;
        barrager.setSpeed((int)speed);
        barrager.setColor("#fff");
        String imgsrc=username+".png";
        barrager.setImg(imgsrc);
        barragerService.addBarrager(barrager);
        return "0000";
    }

}
