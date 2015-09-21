package org.anima.tinytypes.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import org.anima.tinytypes.StringTinyType;

public class StringTinyTypeKeySerializer<T extends StringTinyType> extends JsonSerializer<T> {

    private final Class<T> type;

    public StringTinyTypeKeySerializer(Class<T> type) {
        this.type = type;
    }

    @Override
    public void serialize(T value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        gen.writeFieldName(value.value);
    }

}
