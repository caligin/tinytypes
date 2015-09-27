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
import org.anima.tinytypes.LongTinyType;
import org.anima.tinytypes.meta.LongTinyTypes;

public class LongTinyTypesSerializers extends Serializers.Base {

    @Override
    public JsonSerializer<?> findSerializer(SerializationConfig config, JavaType type, BeanDescription beanDesc) {
        Class<?> candidateTT = type.getRawClass();
        if (LongTinyTypes.includes(candidateTT)) {
            return new LongTinyTypeSerializer();
        }
        return super.findSerializer(config, type, beanDesc);
    }

    public class LongTinyTypeSerializer<T extends LongTinyType> extends JsonSerializer<T> {

        @Override
        public void serialize(T value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
            gen.writeNumber(value.value);
        }

    }

}
