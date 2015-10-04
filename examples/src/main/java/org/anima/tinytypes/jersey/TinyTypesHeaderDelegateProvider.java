package org.anima.tinytypes.jersey;

import javax.ws.rs.ext.Provider;
import org.anima.tinytypes.meta.MetaTinyType;
import org.anima.tinytypes.meta.MetaTinyTypes;
import org.glassfish.jersey.spi.HeaderDelegateProvider;

@Provider
public class TinyTypesHeaderDelegateProvider<T> implements HeaderDelegateProvider<T> {

    private final Class<T> type;
    private final MetaTinyType<T> meta;

    public TinyTypesHeaderDelegateProvider(Class<T> concrete) {
        this.type = concrete;
        this.meta = MetaTinyTypes.metaFor(concrete);
    }

    @Override
    public boolean supports(Class<?> type) {
        return this.type.equals(type);
    }

    @Override
    public String toString(T value) {
        return meta.stringify(value);
    }

    @Override
    public T fromString(String value) {
        return meta.fromString(type, value);
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TinyTypesHeaderDelegateProvider == false) {
            return false;
        }
        final TinyTypesHeaderDelegateProvider<?> other = (TinyTypesHeaderDelegateProvider<?>) obj;
        if (this.type != other.type) {
            return false;
        }
        return true;
    }

}
