package tech.anima.tinytypes.jackson;

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
import tech.anima.tinytypes.meta.BooleanTinyTypes;
import tech.anima.tinytypes.meta.ByteTinyTypes;
import tech.anima.tinytypes.meta.IntTinyTypes;
import tech.anima.tinytypes.meta.LongTinyTypes;
import tech.anima.tinytypes.meta.MetaTinyTypes;
import tech.anima.tinytypes.meta.ShortTinyTypes;
import tech.anima.tinytypes.meta.StringTinyTypes;

public class TinyTypesDeserializers extends Deserializers.Base {

    @Override
    public JsonDeserializer<?> findBeanDeserializer(JavaType type, DeserializationConfig config, BeanDescription beanDesc) throws JsonMappingException {
        Class<?> candidateTT = type.getRawClass();
        if (MetaTinyTypes.isTinyType(candidateTT)) {
            return new TinyTypesDeserializer(candidateTT);
        }
        return null;
    }

    public static class TinyTypesDeserializer<T> extends JsonDeserializer<T> {

        private final Class<T> type;

        public TinyTypesDeserializer(Class<T> type) {
            if (type == null || !MetaTinyTypes.isTinyType(type)) {
                throw new IllegalArgumentException(String.format("not a tinytype: %s", type == null ? "null" : type.getCanonicalName()));
            }
            this.type = type;
        }

        @Override
        public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            return MetaTinyTypes.metaFor(type).newInstance(type, extractValue(p));
        }

        private Object extractValue(JsonParser p) throws IOException {
            if (StringTinyTypes.includes(type)) {
                return p.getText();
            }
            if (BooleanTinyTypes.includes(type)) {
                return p.getBooleanValue();
            }
            if (ByteTinyTypes.includes(type)) {
                return p.getByteValue();
            }
            if (ShortTinyTypes.includes(type)) {
                return p.getShortValue();
            }
            if (IntTinyTypes.includes(type)) {
                return p.getIntValue();
            }
            if (LongTinyTypes.includes(type)) {
                return p.getLongValue();
            }
            throw new IllegalStateException(String.format("not a tinytype: %s, this call should not be possible (typing enforced by ctor)", type.getCanonicalName()));
        }

    }

}
