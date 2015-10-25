package org.anima.tinytypes.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.deser.KeyDeserializers;
import java.io.IOException;
import org.anima.tinytypes.meta.MetaTinyTypes;

public class TinyTypesKeyDeserializers implements KeyDeserializers {

    @Override
    public KeyDeserializer findKeyDeserializer(JavaType type, DeserializationConfig config, BeanDescription beanDesc) throws JsonMappingException {
        final Class<?> candidateTT = type.getRawClass();
        if (MetaTinyTypes.isTinyType(candidateTT)) {
            return new TinyTypesKeyDeserializer(candidateTT);
        }

        return null;
    }

    public static class TinyTypesKeyDeserializer extends KeyDeserializer {

        private final Class<?> type;

        public TinyTypesKeyDeserializer(Class<?> type) {
            if (type == null || !MetaTinyTypes.isTinyType(type)) {
                throw new IllegalArgumentException(String.format("not a tinytype: %s", type == null ? "null" : type.getCanonicalName()));
            }
            this.type = type;
        }

        @Override
        public Object deserializeKey(String key, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            return MetaTinyTypes.metaFor(type).fromString(type, key);
        }

    }
}
