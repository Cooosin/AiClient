package com.coosin.aiclient.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

/**
 * AiAssistant
 *
 * @author coosin
 * @date 2025/05/22 17:35
 */
public interface ToolAiAssistant {

    String chat(String message);

    String chat(@MemoryId int memoryId, @UserMessage String userMessage);

    @SystemMessage(fromResource = "TravelGuidePrompt.txt")
    String chatByTravelGuide(@MemoryId int memoryId, @UserMessage String userMessage);

    @SystemMessage(fromResource = "TravelHtmlPrompt.txt")
    String chatByTravelHtmlCreator(@MemoryId int memoryId, @UserMessage String userMessage);

}
