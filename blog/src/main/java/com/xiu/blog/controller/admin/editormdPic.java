package com.xiu.blog.controller.admin;


import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@ResponseBody
@RequestMapping("/admin/markdown")
public class editormdPic {

    @RequestMapping("/editormdPic")
    @ResponseBody
    public JSONObject editorMd (@RequestParam(value = "editormd-image-file", required = true) MultipartFile file,
                                   HttpServletRequest request,
                                   HttpServletResponse response) throws Exception{

        String trueFileName = file.getOriginalFilename();
        assert trueFileName != null;
        String suffix = trueFileName.substring(trueFileName.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString().replace("-","")+suffix;


        String path = ResourceUtils.getURL("classpath:").getPath();
        File targetFile = new File(path,"static/images/upload/" + fileName);


        if(!targetFile.exists()){
            targetFile.mkdirs();
        }

        //保存
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject res = new JSONObject();
        //这就是返回给页面的json数据
        res.put("url","http://127.0.0.1:3000"+"/images/upload/"+fileName);
        res.put("success", 1);
        res.put("message", "upload success!");

        return res;

    }


}





