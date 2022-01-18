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
