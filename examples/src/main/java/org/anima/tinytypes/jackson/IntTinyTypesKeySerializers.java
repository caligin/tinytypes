package org.anima.tinytypes.jackson;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.anima.tinytypes.IntTinyType;

public class IntTinyTypesKeySerializers extends Serializers.Base {

    @Override
    public JsonSerializer<?> findSerializer(SerializationConfig config, JavaType type, BeanDescription beanDesc) {
        Class<?> candidateTT = type.getRawClass();
        if (IntTinyType.class.equals(candidateTT.getSuperclass())) {
            return new IntTinyTypeKeySerializer(candidateTT);
        }
        return super.findSerializer(config, type, beanDesc);
    }

}
