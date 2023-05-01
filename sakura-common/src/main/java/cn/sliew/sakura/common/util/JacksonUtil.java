package cn.sliew.sakura.common.util;

import cn.sliew.sakura.common.exception.Rethrower;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.core.type.TypeReference;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.DeserializationFeature;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JavaType;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.type.CollectionType;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * jackson utility class.
 */
@Slf4j
public class JacksonUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.registerModule(new Jdk8Module());
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private JacksonUtil() {
        throw new AssertionError("No instances intended");
    }

    /**
     * serialize object to json string.
     */
    public static String toJsonString(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("json failed when serializing object: {}", object, e);
            Rethrower.throwAs(e);
            return null;
        }
    }

    /**
     * deserialize json string to target specified by {@link Class}.
     */
    public static <T> T parseJsonString(String json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("json failed when deserializing clazz: {}, json: {}", clazz.getName(), json, e);
            Rethrower.throwAs(e);
            return null;
        }
    }

    /**
     * deserialize json string to target specified by {@link TypeReference}.
     * {@link TypeReference} indicate type generics.
     */
    public static <T> T parseJsonString(String json, TypeReference<T> reference) {
        try {
            return OBJECT_MAPPER.readValue(json, reference);
        } catch (JsonProcessingException e) {
            log.error("json failed when deserializing clazz: {}, json: {}", reference.getType().getTypeName(), json, e);
            Rethrower.throwAs(e);
            return null;
        }
    }

    /**
     * deserialize json string to target specified by generic type.
     */
    public static <T> T parseJsonString(String json, Class<T> outerType, Class parameterClasses) {
        try {
            JavaType type = OBJECT_MAPPER.getTypeFactory().constructParametricType(outerType, parameterClasses);
            return OBJECT_MAPPER.readValue(json, type);
        } catch (JsonProcessingException e) {
            log.error("json failed when deserializing clazz: {}, json: {}", outerType.getTypeName(), json, e);
            Rethrower.throwAs(e);
            return null;
        }
    }

    public static <T> List<T> parseJsonArray(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json)) {
            return Collections.emptyList();
        }

        try {
            CollectionType listType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, clazz);
            return OBJECT_MAPPER.readValue(json, listType);
        } catch (Exception e) {
            log.error("json failed when deserializing clazz: {}, json: {}", clazz.getName(), json, e);
        }

        return Collections.emptyList();
    }

    public static ArrayNode createArrayNode() {
        return OBJECT_MAPPER.createArrayNode();
    }

    public static ObjectNode createObjectNode() {
        return OBJECT_MAPPER.createObjectNode();
    }

    public static JsonNode toJsonNode(Object obj) {
        return OBJECT_MAPPER.valueToTree(obj);
    }

    public static JsonNode toJsonNode(String json) {
        try {
            return OBJECT_MAPPER.readTree(json);
        } catch (JsonProcessingException e) {
            Rethrower.throwAs(e);
            return null;
        }
    }

    public static <T> T toObject(JsonNode jsonNode, Class<T> clazz) {
        return OBJECT_MAPPER.convertValue(jsonNode, clazz);
    }

    public static <T> T toObject(JsonNode jsonNode, TypeReference<T> typeReference) {
        return OBJECT_MAPPER.convertValue(jsonNode, typeReference);
    }

    public static boolean checkJsonValid(String json) {
        if (StringUtils.isBlank(json)) {
            return false;
        }

        try {
            OBJECT_MAPPER.readTree(json);
            return true;
        } catch (IOException ignored) {
            // just ignore
        }

        return false;
    }
}
