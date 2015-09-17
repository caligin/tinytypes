package org.anima.tinytypes.jersey;

import java.lang.reflect.InvocationTargetException;
import javax.ws.rs.ext.Provider;
import org.anima.tinytypes.StringTinyType;
import org.glassfish.jersey.spi.HeaderDelegateProvider;

@Provider
public class StringTTHeaderDelegateProvider<T extends StringTinyType> implements HeaderDelegateProvider<T> {

    private final Class<T> concrete;

    public StringTTHeaderDelegateProvider(Class<T> concrete) {
        this.concrete = concrete;
    }

    @Override
    public boolean supports(Class<?> type) {
        return concrete.equals(type);
    }

    @Override
    public String toString(StringTinyType value) {
        return value.value;
    }

    @Override
    public T fromString(String value) {
        try {
            return concrete.getConstructor(String.class).newInstance(value);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }

}
