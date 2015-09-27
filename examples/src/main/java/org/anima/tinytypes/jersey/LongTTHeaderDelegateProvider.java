package org.anima.tinytypes.jersey;

import java.lang.reflect.InvocationTargetException;
import javax.ws.rs.ext.Provider;
import org.anima.tinytypes.LongTinyType;
import org.glassfish.jersey.spi.HeaderDelegateProvider;

@Provider
public class LongTTHeaderDelegateProvider<T extends LongTinyType> implements HeaderDelegateProvider<T> {

    private final Class<T> concrete;

    public LongTTHeaderDelegateProvider(Class<T> concrete) {
        this.concrete = concrete;
    }

    @Override
    public boolean supports(Class<?> type) {
        return concrete.equals(type);
    }

    @Override
    public String toString(LongTinyType value) {
        return Long.toString(value.value);
    }

    @Override
    public T fromString(String value) {
        try {
            return concrete.getConstructor(long.class).newInstance(Long.parseLong(value));
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }

}
