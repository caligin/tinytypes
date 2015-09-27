package org.anima.tinytypes.jersey;

import java.lang.reflect.InvocationTargetException;
import javax.ws.rs.ext.Provider;
import org.anima.tinytypes.ShortTinyType;
import org.glassfish.jersey.spi.HeaderDelegateProvider;

@Provider
public class ShortTTHeaderDelegateProvider<T extends ShortTinyType> implements HeaderDelegateProvider<T> {

    private final Class<T> concrete;

    public ShortTTHeaderDelegateProvider(Class<T> concrete) {
        this.concrete = concrete;
    }

    @Override
    public boolean supports(Class<?> type) {
        return concrete.equals(type);
    }

    @Override
    public String toString(ShortTinyType value) {
        return Integer.toString(value.value);
    }

    @Override
    public T fromString(String value) {
        try {
            return concrete.getConstructor(short.class).newInstance(Short.parseShort(value));
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }

}
