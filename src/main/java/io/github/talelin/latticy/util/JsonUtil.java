package io.github.talelin.latticy.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import io.github.talelin.autoconfigure.exception.FailedException;
import org.springframework.util.StringUtils;

public class JsonUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String objectToJson(Object object){
        if (object == null){
            return null;
        }
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new FailedException(9999);
        }
    }

    public static<T> T jsonToObject(String str, TypeReference<T> typeReference){
        if (StringUtils.isEmpty(str)){
            return null;
        }
        try {
            return objectMapper.readValue(str, typeReference);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new FailedException(9999);
        }
    }
}
