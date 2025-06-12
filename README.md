## 一个智能旅行规划助手：能够整合多种信息源：包括**小红书的热门攻略**、**实时天气预报**以及**高德地图的路线规划**去生成一份详尽的旅行计划并以美化后的HTML页面呈现出来。



### 使用教程：


1. **申请一个高德地图的ApiKey,用作远程SSE链接官方的MCP服务**：https://lbs.amap.com/
3. **申请一个和风天气的ApiKey，用作自己构建可以进行SSE链接的MCP服务**：http://dev.qweather.com/
4. **下载一个获取小红书内容的mcp服务，根据项目中的步骤安装相关依赖和cookie**：https://github.com/jobsonlook/xhs-mcp

- 修改配置文件：

```yaml
# 这里填写大模型的ApiKey和模型
llm:
  apiKey: "YOUR_GEMINI_API_KEY"
  modelName: "gemini-1.5-flash-latest"

# 这里填写高德地图的MCP-URL
mcp:
  gaoDe:
    url: "https://mcp.amap.com/sse?key=YOUR_GAODE_API_KEY"
```
二、接口调用:

`curl --location 'http://localhost:8182/travel/chat?content=xxx`

