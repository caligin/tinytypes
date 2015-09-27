package org.anima.tinytypes.jersey;

import java.lang.reflect.InvocationTargetException;
import javax.ws.rs.ext.Provider;
import org.anima.tinytypes.BooleanTinyType;
import org.glassfish.jersey.spi.HeaderDelegateProvider;

@Provider
public class BooleanTTHeaderDelegateProvider<T extends BooleanTinyType> implements HeaderDelegateProvider<T> {

    private final Class<T> concrete;

    public BooleanTTHeaderDelegateProvider(Class<T> concrete) {
        this.concrete = concrete;
    }

    @Override
    public boolean supports(Class<?> type) {
        return concrete.equals(type);
    }

    @Override
    public String toString(BooleanTinyType value) {
        return Boolean.toString(value.value);
    }

    @Override
    public T fromString(String value) {
        try {
            return concrete.getConstructor(boolean.class).newInstance(Boolean.parseBoolean(value));
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }

}
