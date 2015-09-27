package org.anima.tinytypes.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.Serializers;
import java.io.IOException;
import org.anima.tinytypes.BooleanTinyType;
import org.anima.tinytypes.meta.BooleanTinyTypes;

public class BooleanTinyTypesKeySerializers extends Serializers.Base {

    @Override
    public JsonSerializer<?> findSerializer(SerializationConfig config, JavaType type, BeanDescription beanDesc) {
        Class<?> candidateTT = type.getRawClass();
        if (BooleanTinyTypes.includes(candidateTT)) {
            return new BooleanTinyTypeKeySerializer();
        }
        return super.findSerializer(config, type, beanDesc);
    }

    public static class BooleanTinyTypeKeySerializer<T extends BooleanTinyType> extends JsonSerializer<T> {

        @Override
        public void serialize(T value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
            gen.writeFieldName(Boolean.toString(value.value));
        }

    }

}
