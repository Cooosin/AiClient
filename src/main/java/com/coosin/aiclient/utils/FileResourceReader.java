package com.coosin.aiclient.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * FileResourceReader
 *
 * @author coosin
 * @date 2025/06/05 11:21
 */
@Component
public class FileResourceReader {

    @Autowired
    private ResourceLoader resourceLoader;

    public String readFileToString(String filePath) throws IOException{
        // "classpath:" 前缀表示从类路径（包含 resources 目录）下加载
        Resource resource = resourceLoader.getResource("classpath:" + filePath);
        try (InputStream inputStream = resource.getInputStream()) {
            byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
            return new String(bdata, StandardCharsets.UTF_8);
        }
    }

    public InputStream readFileToInputStream(String filePath) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + filePath);
        return resource.getInputStream();
    }
}
