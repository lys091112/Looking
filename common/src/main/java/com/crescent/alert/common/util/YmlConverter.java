package com.crescent.alert.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLParser;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class YmlConverter<T> {

    private YAMLFactory yamlFactory;
    private ObjectMapper mapper;
    private TypeReference<T> clazz;

    YmlConverter(TypeReference<T> clazz) {
        this.clazz = clazz;
        this.yamlFactory = new YAMLFactory();
        this.mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public T build(String path) throws IOException {
        try (InputStream input = this.getClass().getClassLoader().getResourceAsStream(path)) {
            if (null == input) {
                log.error("can't find config file! filePath: {}", path);
                throw new FileNotFoundException();
            }

            YAMLParser yamlParser = yamlFactory.createParser(input);
            JsonNode node = transform(yamlParser);

            if (null == node) {
                log.error("there's no config with class :{}, please check the config file", clazz.getType());
                throw new RuntimeException("invalid config file");
            }

            TreeTraversingParser treeTraversingParser = new TreeTraversingParser(node);
            return mapper.readValue(treeTraversingParser, clazz);
        } catch (Exception e) {
            log.error("exception:{}", e);
            throw e;
        }
    }

    /**
     * 从ymlparser中提取需要的前缀信息，当前只支持List类型和普通类类型
     */
    private JsonNode transform(YAMLParser yamlParser) throws IOException {
        JsonNode node = mapper.readTree(yamlParser);

        Class targetClazz = null;
        Type type = this.clazz.getType();
        if (type instanceof ParameterizedType) {
            if (List.class.isAssignableFrom((Class<?>) ((ParameterizedType) type).getRawType())) {
                targetClazz = ((Class) ((ParameterizedType) type).getActualTypeArguments()[0]);
            }
        } else if (type instanceof Class) {
            targetClazz = (Class) type;
        } else {
            throw new IllegalArgumentException("not support type! type:" + type.getTypeName());
        }

        if (null != targetClazz && targetClazz.isAnnotationPresent(ConfigPrefix.class)) {
            String prefix = ((ConfigPrefix) targetClazz.getAnnotation(ConfigPrefix.class)).value();
            return node.get(prefix);
        }
        return node;
    }

    public static <T> T config(TypeReference<T> clazz, String configPath) throws IOException {
        return new YmlConverter<T>(clazz).build(configPath);
    }

}
