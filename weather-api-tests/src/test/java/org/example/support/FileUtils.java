package org.example.support;

import java.io.InputStream;

public class FileUtils {
    public static InputStream getResourceFile(String path) {
        return FileUtils.class.getClassLoader().getResourceAsStream(path);
    }
}
