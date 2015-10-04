package org.anima.tinytypes.jersey;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import org.anima.tinytypes.meta.MetaTinyType;
import org.anima.tinytypes.meta.MetaTinyTypes;

@Provider
public class TinyTypesParamProvider implements ParamConverterProvider {

    @Override
    public <T> ParamConverter<T> getConverter(final Class<T> rawType, Type genericType, Annotation[] annotations) {
        if (MetaTinyTypes.isTinyType(rawType)) {
            return new TinyTypesParamConverter<>(rawType);
        }
        return null;
    }

    public static class TinyTypesParamConverter<T> implements ParamConverter<T> {

        private final Class<T> type;
        private final MetaTinyType<T> meta;

        public TinyTypesParamConverter(Class<T> type) {
            this.type = type;
            this.meta = MetaTinyTypes.metaFor(type);
        }

        @Override
        public T fromString(String value) {
            return meta.fromString(type, value);
        }

        @Override
        public String toString(T value) {
            return meta.stringify(value);
        }
    }

}
