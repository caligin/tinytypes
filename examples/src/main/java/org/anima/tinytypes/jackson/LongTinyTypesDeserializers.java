package org.anima.tinytypes.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.Deserializers;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.anima.tinytypes.LongTinyType;
import org.anima.tinytypes.meta.LongTinyTypes;

public class LongTinyTypesDeserializers extends Deserializers.Base {

    @Override
    public JsonDeserializer<?> findBeanDeserializer(JavaType type, DeserializationConfig config, BeanDescription beanDesc) throws JsonMappingException {
        Class<?> candidateTT = type.getRawClass();
        if (LongTinyTypes.includes(candidateTT)) {
            return new LongTinyTypeDeserializer(candidateTT);
        }
        return null;
    }

    public static class LongTinyTypeDeserializer<T extends LongTinyType> extends JsonDeserializer<T> {

        private final Class<T> rawType;

        public LongTinyTypeDeserializer(Class<T> rawType) {
            this.rawType = rawType;
        }

        @Override
        public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            try {
                return rawType.getConstructor(long.class).newInstance(p.getLongValue());
            } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

}
