package com.coosin.aiclient.service;

import com.coosin.aiclient.utils.FileResourceReader;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * TravelService
 *
 * @author coosin
 * @date 2025/05/29 20:44
 */
@Service
@Slf4j
public class TravelService {


    private static final String OUTPUT_HTML_FILE = "travel.html";

    @Resource
    private ToolAiAssistant toolAiAssistant;

    @Resource
    private NormalAiAssistant normalAiAssistant;


    public void getTravelGuide(String require) {
        //1.旅游攻略生成
        String response = toolAiAssistant.chatByTravelGuide(1, require);
        log.info("旅行规划表响应：{}", response);
        //2.html美化
        log.info("开始生成HTML代码...");

        String htmlCode = normalAiAssistant.chatByTravelHtmlCreator(2, response);
        log.info("生成的HTML代码：{}", htmlCode);
        //清理返回的非法格式
        htmlCode=clearHtmlCode(htmlCode);
        String fileName = OUTPUT_HTML_FILE;
        try {
            if (!Files.exists(Paths.get(fileName))) {
                Files.createFile(Paths.get(fileName));
            }
            Files.write(Paths.get(fileName), htmlCode.getBytes());
            log.info("HTML代码已保存到文件：{}", fileName);
            //String checkCode = normalAiAssistant.chatByDoubleCheck(3, htmlCode);
            //log.info("双重检查响应：{}", checkCode);
            //String travelCheck = "travelCheck.html";
            //if (!Files.exists(Paths.get(travelCheck))) {
            //    Files.createFile(Paths.get(travelCheck));
            //}
            //Files.write(Paths.get(travelCheck), checkCode.getBytes());
        } catch (IOException e) {
            log.error("保存HTML代码时出错：{}", e.getMessage());
        }


    }

    private String clearHtmlCode(String htmlCode) {
        //检查是否开头为```html
        if (htmlCode.startsWith("```html")) {
            //将htmlCode中的开头的```html替换为空，用替换前几位字符串的方式去替换
            htmlCode = htmlCode.substring(7);
        }
        //检查是否结尾为```
        if (htmlCode.endsWith("```")) {
            //将htmlCode中的结尾的```替换为空，用替换最后几位字符串的方式去替换
            htmlCode = htmlCode.substring(0, htmlCode.length() - 3);
        }
        return htmlCode;
    }

}


