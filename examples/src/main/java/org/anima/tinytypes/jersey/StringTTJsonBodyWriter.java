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
import org.anima.tinytypes.StringTinyType;

@Produces(value = "application/json;charset=utf-8")
public class StringTTJsonBodyWriter implements MessageBodyWriter<StringTinyType> {

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return type.getSuperclass().equals(StringTinyType.class);
    }

    @Override
    public long getSize(StringTinyType t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(StringTinyType t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        entityStream.write('"');
        entityStream.write(t.value.getBytes(StandardCharsets.UTF_8));
        entityStream.write('"');
    }

}
