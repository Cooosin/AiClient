package com.coosin.aiclient;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.mcp.client.McpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class    AiClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiClientApplication.class, args);
    }


}
