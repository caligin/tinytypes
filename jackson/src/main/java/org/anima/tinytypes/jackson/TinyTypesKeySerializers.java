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
import org.anima.tinytypes.meta.MetaTinyType;
import org.anima.tinytypes.meta.MetaTinyTypes;

public class TinyTypesKeySerializers extends Serializers.Base {

    @Override
    public JsonSerializer<?> findSerializer(SerializationConfig config, JavaType type, BeanDescription beanDesc) {
        Class<?> candidateTT = type.getRawClass();
        if (MetaTinyTypes.isTinyType(candidateTT)) {
            return new TinyTypesKeySerializer<>(candidateTT);
        }
        return super.findSerializer(config, type, beanDesc);
    }

    public static class TinyTypesKeySerializer<T> extends JsonSerializer<T> {

        private final MetaTinyType<T> meta;

        public TinyTypesKeySerializer(Class<T> tt) {
            if (tt == null) {
                throw new IllegalArgumentException(String.format("not a tinytype: %s", tt == null ? "null" : tt.getCanonicalName()));
            }
            this.meta = MetaTinyTypes.metaFor(tt);
        }

        @Override
        public void serialize(T value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
            gen.writeFieldName(meta.stringify(value));
        }

    }

}
