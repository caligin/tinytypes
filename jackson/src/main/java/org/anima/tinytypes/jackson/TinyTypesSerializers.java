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
import org.anima.tinytypes.ByteTinyType;
import org.anima.tinytypes.IntTinyType;
import org.anima.tinytypes.LongTinyType;
import org.anima.tinytypes.ShortTinyType;
import org.anima.tinytypes.StringTinyType;
import org.anima.tinytypes.meta.BooleanTinyTypes;
import org.anima.tinytypes.meta.ByteTinyTypes;
import org.anima.tinytypes.meta.IntTinyTypes;
import org.anima.tinytypes.meta.LongTinyTypes;
import org.anima.tinytypes.meta.MetaTinyTypes;
import org.anima.tinytypes.meta.ShortTinyTypes;
import org.anima.tinytypes.meta.StringTinyTypes;

public class TinyTypesSerializers extends Serializers.Base {

    @Override
    public JsonSerializer<?> findSerializer(SerializationConfig config, JavaType type, BeanDescription beanDesc) {
        Class<?> candidateTT = type.getRawClass();
        if (MetaTinyTypes.isTinyType(candidateTT)) {
            return new TinyTypesSerializer(candidateTT);
        }
        return super.findSerializer(config, type, beanDesc);
    }

    public static class TinyTypesSerializer<T> extends JsonSerializer<T> {

        private final Class<T> type;

        private TinyTypesSerializer(Class<T> type) {
            if (!MetaTinyTypes.isTinyType(type)) {
                throw new IllegalArgumentException(String.format("not a tinytype: %s", type.getCanonicalName()));
            }
            this.type = type;
        }

        @Override
        public void serialize(T value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
            if (StringTinyTypes.includes(type)) {
                gen.writeString(((StringTinyType) value).value);
                return;
            }
            if (BooleanTinyTypes.includes(type)) {
                gen.writeBoolean(((BooleanTinyType) value).value);
                return;
            }
            if (ByteTinyTypes.includes(type)) {
                gen.writeNumber(((ByteTinyType) value).value);
                return;
            }
            if (ShortTinyTypes.includes(type)) {
                gen.writeNumber(((ShortTinyType) value).value);
                return;
            }
            if (IntTinyTypes.includes(type)) {
                gen.writeNumber(((IntTinyType) value).value);
                return;
            }
            if (LongTinyTypes.includes(type)) {
                gen.writeNumber(((LongTinyType) value).value);
                return;
            }
            throw new IllegalStateException(String.format("not a tinytype: %s, this call should not be possible (typing enforced by ctor)", value.getClass().getCanonicalName()));
        }

    }

}
