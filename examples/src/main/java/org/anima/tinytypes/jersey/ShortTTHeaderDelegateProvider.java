package org.anima.tinytypes.jersey;

import java.lang.reflect.InvocationTargetException;
import javax.ws.rs.ext.Provider;
import org.anima.tinytypes.ByteTinyType;
import org.glassfish.jersey.spi.HeaderDelegateProvider;

@Provider
public class ByteTTHeaderDelegateProvider<T extends ByteTinyType> implements HeaderDelegateProvider<T> {

    private final Class<T> concrete;

    public ByteTTHeaderDelegateProvider(Class<T> concrete) {
        this.concrete = concrete;
    }

    @Override
    public boolean supports(Class<?> type) {
        return concrete.equals(type);
    }

    @Override
    public String toString(ByteTinyType value) {
        return Integer.toString(value.value);
    }

    @Override
    public T fromString(String value) {
        try {
            return concrete.getConstructor(byte.class).newInstance(Byte.parseByte(value));
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }

}
