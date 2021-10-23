package io.github.talelin.latticy.type_handler;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.io.IOException;

@Slf4j
@MappedTypes({Object.class})
@MappedJdbcTypes(JdbcType.VARCHAR)
public class JacksonTypeHandler2<T extends Object>  extends AbstractJsonTypeHandler<T> {
    private static ObjectMapper objectMapper = new ObjectMapper();
    private Class<Object> type;

    public JacksonTypeHandler2(Class<Object> type) {
        if (log.isTraceEnabled()) {
            log.trace("JacksonTypeHandler(" + type + ")");
        }
        Assert.notNull(type, "Type argument cannot be null");
        this.type = type;
    }

    @Override
    protected T parse(String json) {
        if (json == null){
            return null;
        }
        try {
            return objectMapper.readValue(json, new TypeReference<T>() {
            });
        }catch (IOException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    protected String toJson(T obj) {
        return null;
    }


}
