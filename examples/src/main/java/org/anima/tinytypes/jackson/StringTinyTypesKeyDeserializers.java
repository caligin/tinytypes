package org.anima.tinytypes.jackson;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.deser.KeyDeserializers;
import org.anima.tinytypes.StringTinyType;

public class StringTinyTypesKeyDeserializers implements KeyDeserializers {

    @Override
    public KeyDeserializer findKeyDeserializer(JavaType type, DeserializationConfig config, BeanDescription beanDesc) throws JsonMappingException {
        Class<?> candidateTT = type.getRawClass();
        if (StringTinyType.class.equals(candidateTT.getSuperclass())) {
            return new StringTinyTypeKeyDeserializer(candidateTT);
        }
        return null;
    }

}
