package com.coosin.aiclient.config;

import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.mcp.client.transport.http.HttpMcpTransport;
import dev.langchain4j.mcp.client.transport.stdio.StdioMcpTransport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * MCPConfig
 *
 * @author coosin
 * @date 2025/05/23 10:13
 */
@Configuration
public class MCPConfig {

    @Value("${mcp.gaoDe.url:}")
    private String gaoDeUrl;

    /**
     * 自建天气SSE MCP服务
     * @return
     */
    @Bean
    public McpClient mcpClientWeather() {
        McpTransport transport = new HttpMcpTransport.Builder()
                .sseUrl("http://localhost:8083/sse")
                .logRequests(true) // if you want to see the traffic in the log
                .logResponses(true)
                .build();
        return new DefaultMcpClient.Builder()
                .transport(transport)
                .build();
    }

    /**
     * 本地RedNoteBook服务
     * @return
     */
    @Bean
    public McpClient mcpClientRedNoteBookLocal() {
        return new DefaultMcpClient.Builder()
                .transport(new StdioMcpTransport.Builder()
                        .command(List.of(
                                "npx",
                                "-y",
                                "@smithery/cli@latest",
                                "run",
                                "@jobsonlook/xhs-mcp",
                                "--key",
                                "xxxx",
                                "--profile",
                                "wittering-pheasant-r5Qlxf"
                        ))
                        //.logEvents(true) // only if you want to see the traffic in the log
                        .build())
                .build();
    }

    /**
     * 高德地图SSE MCP服务
     * @return
     */
    @Bean
    public McpClient mcpClientGaoDeMap() {
        McpTransport transport = new HttpMcpTransport.Builder()
                .sseUrl(gaoDeUrl)
                //.logRequests(true)
                //.logResponses(true)
                .build();
        return new DefaultMcpClient.Builder()
                .transport(transport)
                .build();
    }

    //@Bean
    //public McpClient mcpClientRedNoteBookLocal() {
    //    return new DefaultMcpClient.Builder()
    //            .transport(new StdioMcpTransport.Builder()
    //                    .command(List.of(
    //                            "rednote-mcp",
    //                            "--stdio"
    //                    ))
    //                    .logEvents(true) // only if you want to see the traffic in the log
    //                    .build())
    //            .build();
    //}


    //@Bean
    //public McpClient mcpClient12306() {
    //    McpTransport transport = new HttpMcpTransport.Builder()
    //            .sseUrl("xxx")
    //            .logRequests(true) // if you want to see the traffic in the log
    //            .logResponses(true)
    //            .build();
    //    return new DefaultMcpClient.Builder()
    //            .transport(transport)
    //            .build();
    //}


    /**
     * 把上述所有的MCP客户端注入到McpToolProvider中
     * @param clients
     * @return
     */
    @Bean
    public McpToolProvider mcpToolProvider(List<McpClient> clients) {
        return McpToolProvider.builder()
                .mcpClients(clients)
                .build();
    }


}
