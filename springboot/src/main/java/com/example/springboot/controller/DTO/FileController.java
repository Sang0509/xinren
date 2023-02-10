package com.example.springboot.controller.DTO;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.example.springboot.dao.FileMapper;
import com.example.springboot.entity.Files;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

//文件上传相关接口
//@param file 前端传递过来的文件
@RestController
@RequestMapping("/file/")
public class FileController {

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Resource
    private FileMapper fileMapper;

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        String getOriginalFilename = file.getOriginalFilename();//原始名称
        String type = FileUtil.extName(getOriginalFilename);//获取文件类型
        long size = file.getSize();//获取大小
        //先存储到磁盘
        File uploadParentFile = new File(fileUploadPath);
        //判断配置的文件目录是否存在，若不存在则创建一个新的文件目录
        if(!uploadParentFile.exists()) {
            uploadParentFile.mkdirs();
        }
        //定义一个文件唯一的标识码
        String uuid = IdUtil.fastSimpleUUID();
        String fileUUID = uuid + StrUtil.DOT + type;
        File uploadFile = new File(fileUploadPath + fileUUID);
        //把获取到的文件存储到磁盘目录
        file.transferTo(uploadFile);

        String url = "http://localhost:9090/file/" + fileUUID;
        //存储数据库
        Files saveFile = new Files();
        saveFile.setName(getOriginalFilename);
        saveFile.setType(type);
        saveFile.setSize(size);
        saveFile.setUrl(url);
        fileMapper.insert(saveFile);
        return url;
    }

    @GetMapping("/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        //根据文件的唯一标识码获取文件
        File uploadFile = new File(fileUploadPath + fileUUID);
        //设置输出流的格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(fileUUID, "UTF-8"));
        response.setContentType("application/octet-stream");

        //读取文件字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }
}
