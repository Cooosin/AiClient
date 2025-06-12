package com.coosin.aiclient.controller;

import com.coosin.aiclient.service.ToolAiAssistant;
import com.coosin.aiclient.service.TravelService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * test
 *
 * @author coosin
 * @date 2025/05/22 17:38
 */
@RestController
@Slf4j
public class TravelChatController {

    @Resource
    private ToolAiAssistant toolAiAssistant;

    @Autowired
    private TravelService travelService;

    @GetMapping("/travel/chat")
    public void travelChat(@RequestParam String content) {
        travelService.getTravelGuide(content);
    }

    @RequestMapping("/test")
    public void test(@RequestParam String content) {
        String chat = toolAiAssistant.chat(content);
        System.out.println("test chat response: " + chat);
    }


}
