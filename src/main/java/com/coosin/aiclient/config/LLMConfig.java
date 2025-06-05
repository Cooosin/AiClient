package com.coosin.aiclient.config;

import com.coosin.aiclient.service.NormalAiAssistant;
import com.coosin.aiclient.service.ToolAiAssistant;
import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.listener.ChatModelErrorContext;
import dev.langchain4j.model.chat.listener.ChatModelListener;
import dev.langchain4j.model.chat.listener.ChatModelResponseContext;
import dev.langchain4j.model.chat.listener.ChatModelRequestContext;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.service.AiServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * LLMConfig
 *
 * @author coosin
 * @date 2025/05/22 17:35
 */
@Configuration
@Slf4j
public class LLMConfig {

    @Value("${llm.apiKey:}")
    private String apiKey;

    @Value("${llm.modelName:}")
    private String modelName;

    /**
     * 阿里云的模型
     *
     * @return
     */
    //@Bean
    //public ChatModel chatLanguageModel() {
    //    return OpenAiChatModel.builder()
    //            .apiKey("sk-b8b88ddeb3274884a92e57df84f3967a")
    //            .modelName("qwen-plus-latest")
    //            .maxRetries(1)
    //            .maxTokens(8192)
    //            .timeout(Duration.ofMinutes(60))
    //            .logRequests(true)
    //            .logResponses(true)
    //            .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
    //            .build();
    //}


    /**
     * Google Gemini模型
     *
     * @return
     */
    @Bean
    public ChatModel chatLanguageModel() {
        return GoogleAiGeminiChatModel.builder()
                .apiKey(apiKey)
                .modelName(modelName)
                .timeout(Duration.ofMinutes(5))
                .maxRetries(1)
                .logRequestsAndResponses(true)
                .build();
    }


    /**
     * 使用LangChain4J的高级API来构建一个AI助手，注入MCP Client
     * 构建可以使用工具的AI助手
     *
     * @return
     */
    @Bean
    public ToolAiAssistant aiAssistant(McpToolProvider mcpToolProvider) {
        return AiServices.builder(ToolAiAssistant.class)
                .chatModel(chatLanguageModel())
                .chatMemoryProvider(memoryId -> MessageWindowChatMemory.withMaxMessages(10))
                .toolProvider(mcpToolProvider)
                .build();
    }

    /**
     * 使用LangChain4J的高级API来构建一个AI助手，注入MCP Client
     * 构建不包含工具的AI助手
     *
     * @return
     */
    @Bean
    public NormalAiAssistant normalAiAssistant() {
        return AiServices.builder(NormalAiAssistant.class)
                .chatModel(chatLanguageModel())
                .chatMemoryProvider(memoryId -> MessageWindowChatMemory.withMaxMessages(10))
                .build();
    }

    @Bean
    ChatModelListener chatModelListener() {
        return new ChatModelListener() {
            @Override
            public void onRequest(ChatModelRequestContext requestContext) {
                //System.out.println("onRequest(): " + requestContext.chatRequest());
                log.info("onRequest(): {}", requestContext.chatRequest());
            }

            @Override
            public void onResponse(ChatModelResponseContext responseContext) {
                log.info("onResponse(): {}", responseContext.chatResponse());
            }

            @Override
            public void onError(ChatModelErrorContext errorContext) {
                log.info("onError(): {}", errorContext.error().getMessage());
            }
        };
    }
}
