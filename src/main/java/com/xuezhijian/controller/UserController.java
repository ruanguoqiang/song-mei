
package com.xuezhijian.controller;

import com.alibaba.fastjson.JSON;
import com.xuezhijian.model.User;
import com.xuezhijian.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/myself")
    public String getMyslefInfo(@RequestParam String username, Model model,HttpServletRequest httpServletRequest){
      User user= userService.findUserByName(username);
      model.addAttribute("usernmae",user.getUsername());
      model.addAttribute("email",user.getEmail());
      String imgsrc="img/"+username+".png";
      httpServletRequest.getSession().setAttribute("imgsrc",imgsrc);
      return "hello";
    }

    @RequestMapping(value="/upLoadPicture",method = RequestMethod.POST)
    public String addFilemsgPic(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception{
        String filePath="/usr/share/ROOT/WEB-INF/classes/static/img/";
        String fileName=(String)request.getSession().getAttribute("name");
        String contentType = file.getContentType();
        fileName = fileName+".png";
        //String filePath = request.getSession().getServletContext().getRealPath("imgupload/");
        try {
            uploadFile(file.getBytes(), filePath, fileName);
        } catch (Exception e) {
            // TODO: handle exception
        }
        //返回json
        return "index3";
    }
    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
    }
}

