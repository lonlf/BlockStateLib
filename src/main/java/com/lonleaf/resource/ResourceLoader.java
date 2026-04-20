package com.lonleaf.resource;

import com.lonleaf.version.Version;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * 资源文件加载器
 */
public class ResourceLoader {

    public static String loadResource(String resourcePath) throws IOException {
        InputStream inputStream = ResourceLoader.class.getResourceAsStream(resourcePath);
        if (inputStream == null) {
            // 尝试从类加载器获取
            inputStream = ResourceLoader.class.getClassLoader().getResourceAsStream(resourcePath);
        }

        if (inputStream == null) {
            throw new IOException("Resource not found: " + resourcePath);
        }

        return readStream(inputStream);
    }

    private static String readStream(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return readContent(reader);
        }
    }

    private static String readContent(BufferedReader reader) throws IOException {
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line);
        }
        return content.toString();
    }

    public static String getResourcePath(Version version) {
        return version.getMinecraftVersion(version)+"/blocks.json";
    }
}

