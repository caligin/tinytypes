package org.anima.tinytypes.jersey;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import org.anima.tinytypes.LongTinyType;

@Provider
public class LongTTParamProvider implements ParamConverterProvider {

    @Override
    public <T> ParamConverter<T> getConverter(final Class<T> rawType, Type genericType, Annotation[] annotations) {
        if (rawType.getSuperclass().equals(LongTinyType.class)) {
            return new ParamConverter<T>() {
                @Override
                public T fromString(String value) {
                    try {
                        return rawType.getConstructor(long.class).newInstance(Long.parseLong(value));
                    } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                @Override
                public String toString(T value) {
                    return Long.toString(((LongTinyType) value).value);
                }
            };
        }
        return null;
    }

}
