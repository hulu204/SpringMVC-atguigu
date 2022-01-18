package com.atguigu.mvc.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author 李聪燕
 * @date 2022/1/18 15:46
 */
@Controller
public class FileController {
    @RequestMapping("/fileDown")
    public ResponseEntity<byte[]> testResponseEntity(HttpSession session) throws IOException {
        ServletContext servletContext = session.getServletContext();
        String realPath = servletContext.getRealPath("/static/阅读顺序.pdf");
        System.out.println(realPath);
        InputStream is = new FileInputStream(realPath);
        byte[] bytes = new byte[is.available()];
        is.read(bytes);
        MultiValueMap<String,String> headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment;filename=阅读顺序.pdf");
        HttpStatus status = HttpStatus.OK;
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes,headers,status);
        is.close();
        return responseEntity;
    }
//    该方法本地测试时文件上传到了D:\apache-tomcat-8.0.50\webapps\SpringMVC中，每次本地重启服务时上传的图片会消失
//    @RequestMapping("/fileUp")
//    public String testUp(MultipartFile photo, HttpSession session) throws IOException {
//        //获取上传的文件的文件名
//        String fileName = photo.getOriginalFilename();
//        //处理文件重名问题
//        String hzName = fileName.substring(fileName.lastIndexOf("."));
//        fileName = UUID.randomUUID().toString() + hzName;
//        //获取服务器中photo目录的路径
//        ServletContext servletContext = session.getServletContext();
//        String photoPath = servletContext.getRealPath("photo");
//        File file = new File(photoPath);
//        if(!file.exists()){
//            file.mkdir();
//        }
//        String finalPath = photoPath + File.separator + fileName;
//        //实现上传功能
//        photo.transferTo(new File(finalPath));
//        return "success";
//    }
    @RequestMapping("/fileUp")
    public String testUp(MultipartFile photo) throws IOException {
        String filename = photo.getOriginalFilename();
        String hzname = filename.substring(filename.lastIndexOf("."));
        filename = UUID.randomUUID().toString() + hzname;
        String photoPath = "D:\\Project\\java\\SpringMVC-atguigu\\target\\SpringMVC-atguigu-1.0-SNAPSHOT\\photo";
        File file = new File(photoPath);
        if (!file.exists()) {
            file.mkdir();
        }
        String finalPath = photoPath + File.separator + filename;
        photo.transferTo(new File(finalPath));
        return "success";
    }
}
