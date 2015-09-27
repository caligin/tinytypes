package org.anima.tinytypes.jersey;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import org.anima.tinytypes.BooleanTinyType;

@Provider
public class BooleanTTParamProvider implements ParamConverterProvider {

    @Override
    public <T> ParamConverter<T> getConverter(final Class<T> rawType, Type genericType, Annotation[] annotations) {
        if (rawType.getSuperclass().equals(BooleanTinyType.class)) {
            return new ParamConverter<T>() {
                @Override
                public T fromString(String value) {
                    try {
                        return rawType.getConstructor(boolean.class).newInstance(Boolean.parseBoolean(value));
                    } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                @Override
                public String toString(T value) {
                    return Boolean.toString(((BooleanTinyType) value).value);
                }
            };
        }
        return null;
    }

}
