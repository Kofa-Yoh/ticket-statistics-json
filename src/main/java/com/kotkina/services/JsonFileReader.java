package com.kotkina.services;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonFileReader {

    public <T> T getContent(String path, Class<T> valueType) throws IOException {
        if (path == null || "".equals(path)) {
            throw new IllegalArgumentException("Неверно указан путь.");
        }

        File file = new File(path);
        if (!file.exists()) {
            throw new IllegalArgumentException("Файл не найден.");
        }

        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        return objectMapper.readValue(file, valueType);
    }
}
