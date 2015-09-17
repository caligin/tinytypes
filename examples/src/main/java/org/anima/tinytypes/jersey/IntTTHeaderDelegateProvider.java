package org.anima.tinytypes.jersey;

import java.lang.reflect.InvocationTargetException;
import javax.ws.rs.ext.Provider;
import org.anima.tinytypes.IntTinyType;
import org.glassfish.jersey.spi.HeaderDelegateProvider;

@Provider
public class IntTTHeaderDelegateProvider<T extends IntTinyType> implements HeaderDelegateProvider<T> {

    private final Class<T> concrete;

    public IntTTHeaderDelegateProvider(Class<T> concrete) {
        this.concrete = concrete;
    }

    @Override
    public boolean supports(Class<?> type) {
        return concrete.equals(type);
    }

    @Override
    public String toString(IntTinyType value) {
        return Integer.toString(value.value);
    }

    @Override
    public T fromString(String value) {
        try {
            return concrete.getConstructor(int.class).newInstance(Integer.parseInt(value));
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }

}
