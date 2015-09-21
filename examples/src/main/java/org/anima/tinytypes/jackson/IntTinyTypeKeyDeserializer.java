package org.anima.tinytypes.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class IntTinyTypeKeyDeserializer extends KeyDeserializer {

    private final Class<?> rawType;

    public IntTinyTypeKeyDeserializer(Class<?> rawType) {
        this.rawType = rawType;
    }

    @Override
    public Object deserializeKey(String key, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        try {
            return rawType.getConstructor(int.class).newInstance(Integer.parseInt(key));
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }

}