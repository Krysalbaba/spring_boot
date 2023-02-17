package com.java.nie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.java.nie.domain.FileInfo;
import com.java.nie.service.IFileInfoService;
import lombok.Cleanup;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author nie
 * @since 2022-07-05
 * @apiNote 测试文件打包为zip返回
 */
@RestController
@RequestMapping("/fileInfo")
public class FileInfoController {

    @Resource
    private IFileInfoService iFileInfoService ;


    @PostMapping("/fileDown")
    public void fileDown(@RequestBody List<String> ids , HttpServletResponse response){
        LambdaQueryWrapper<FileInfo> wrapper =new LambdaQueryWrapper<>();
        wrapper.in(FileInfo::getBusinessId,ids);
        List<FileInfo> list = iFileInfoService.list(wrapper);


        try{
            //zip包的名称
            String downloadFilename = "images.zip";
            downloadFilename = URLEncoder.encode(downloadFilename, "UTF-8");
            // 指明response的返回对象是文件流
            response.setContentType("application/octet-stream");
            // 设置在下载框默认显示的文件名
            response.setHeader("Content-Disposition", "attachment;filename=" + downloadFilename);
            @Cleanup ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
            list.forEach(item->{
                InputStream fis = null ;
                try {
                    ZipEntry zipEntry = new ZipEntry("download_"+item.getBusinessId() + File.separator +list.indexOf(item)+ ".jpg");
                    zos.putNextEntry(zipEntry);
                    fis = getInputStreamByGet(item.getUrl()+"/"+item.getFid());
                    if (null != fis) {
                        byte[] buffer = new byte[1024];
                        int r = 0;
                        // 从输入流读取一定数量的字节，存到缓存区中
                        while ((r = fis.read(buffer)) != -1) {
                            zos.write(buffer, 0, r);
                        }

                    }
                }catch (Exception e){

                }
            });
            zos.flush();
        }catch (Exception e){

        }

    }


    /**
     * 通过get请求得到读取器响应数据的数据流
     * @param url
     * @return
     */
    private static InputStream getInputStreamByGet(String url) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL("http://"+url).openConnection();
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return conn.getInputStream();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

