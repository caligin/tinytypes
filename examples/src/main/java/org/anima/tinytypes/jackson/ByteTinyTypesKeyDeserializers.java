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
import java.lang.reflect.InvocationTargetException;
import org.anima.tinytypes.meta.ByteTinyTypes;

public class ByteTinyTypesKeyDeserializers implements KeyDeserializers {

    @Override
    public KeyDeserializer findKeyDeserializer(JavaType type, DeserializationConfig config, BeanDescription beanDesc) throws JsonMappingException {
        Class<?> candidateTT = type.getRawClass();
        if (ByteTinyTypes.includes(candidateTT)) {
            return new ByteTinyTypeKeyDeserializer(candidateTT);
        }
        return null;
    }

    public static class ByteTinyTypeKeyDeserializer extends KeyDeserializer {

        private final Class<?> rawType;

        public ByteTinyTypeKeyDeserializer(Class<?> rawType) {
            this.rawType = rawType;
        }

        @Override
        public Object deserializeKey(String key, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            try {
                return rawType.getConstructor(byte.class).newInstance(Byte.parseByte(key));
            } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

}
