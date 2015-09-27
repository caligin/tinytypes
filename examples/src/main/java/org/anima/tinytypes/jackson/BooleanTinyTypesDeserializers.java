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
import org.anima.tinytypes.BooleanTinyType;
import org.anima.tinytypes.meta.BooleanTinyTypes;

public class BooleanTinyTypesDeserializers extends Deserializers.Base {

    @Override
    public JsonDeserializer<?> findBeanDeserializer(JavaType type, DeserializationConfig config, BeanDescription beanDesc) throws JsonMappingException {
        Class<?> candidateTT = type.getRawClass();
        if (BooleanTinyTypes.includes(candidateTT)) {
            return new BooleanTinyTypeDeserializer(candidateTT);
        }
        return null;
    }

    public static class BooleanTinyTypeDeserializer<T extends BooleanTinyType> extends JsonDeserializer<T> {

        private final Class<T> rawType;

        public BooleanTinyTypeDeserializer(Class<T> rawType) {
            this.rawType = rawType;
        }

        @Override
        public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            try {
                return rawType.getConstructor(boolean.class).newInstance(p.getBooleanValue());
            } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

}
