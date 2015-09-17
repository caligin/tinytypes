package org.anima.tinytypes.jersey;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import org.anima.tinytypes.IntTinyType;

@Produces(value = "application/json;charset=utf-8")
public class IntTTJsonBodyWriter implements MessageBodyWriter<IntTinyType> {

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return type.getSuperclass().equals(IntTinyType.class);
    }

    @Override
    public long getSize(IntTinyType t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(IntTinyType t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        entityStream.write(Integer.toString(t.value).getBytes(StandardCharsets.UTF_8));
    }

}
