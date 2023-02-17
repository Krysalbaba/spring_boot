package com.java.nie.controller;


import com.alibaba.excel.EasyExcel;
import com.java.nie.common.entity.vo.CompletedExportVo;
import com.java.nie.service.ITCompletedService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author nie
 * @date 2023-02-17
 * @apiNote excel文件导出
 */
@RestController
@RequestMapping("/tCompleted")
@Slf4j
public class TCompletedController {

    @Resource
    private ITCompletedService completedService ;



    @PostMapping("/export")
    @ApiOperation(value = "文件导出")
    public void export(@RequestBody List<String> ids , HttpServletResponse response){
        try{
            List<CompletedExportVo> export = completedService.export(ids);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("竣工记录_" + + System.currentTimeMillis(), "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), CompletedExportVo.class).sheet("竣工记录").doWrite(export);
        }catch (Exception e){
            e.printStackTrace();
            log.info("导出竣工信息失败");
        }
    }

}

