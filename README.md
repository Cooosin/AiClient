## 一个支持小红书搜索，高德地图，和风天气的MCP服务

### 使用教程：

1. **申请一个高德地图的ApiKey**：https://lbs.amap.com/，用作远程SSE链接官方的MCP服务
2. **申请一个和风天气的ApiKey**：http://dev.qweather.com/，用作自己构建可以进行SSE链接的MCP服务
3. **下载一个获取小红书内容的mcp服务**：https://github.com/jobsonlook/xhs-mcp，根据项目中的步骤安装相关依赖和cookie

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
