package org.anima.tinytypes.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.IOException;
import org.anima.tinytypes.jackson.TinyTypesDeserializers.TinyTypesDeserializer;
import org.anima.tinytypes.Samples;
import org.junit.Test;
import org.junit.Assert;
import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.Is.is;

@RunWith(Enclosed.class)
public class TinyTypesDeserializersTest {

    @RunWith(Theories.class)
    public static class FindDeserializer {

        @Test
        public void yieldsNullWhenTypeIsNotATinyType() throws JsonMappingException {
            final JavaType typeForObject = TypeFactory.defaultInstance().uncheckedSimpleType(Object.class);
            final JsonDeserializer<?> got = new TinyTypesDeserializers().findBeanDeserializer(typeForObject, null, null);
            Assert.assertNull(got);
        }

        @DataPoints
        public static final Class<?>[] tinyTypesSamples = new Class<?>[]{
            Samples.Str.class,
            Samples.Boolean.class,
            Samples.Byte.class,
            Samples.Short.class,
            Samples.Integer.class,
            Samples.Long.class};

        @Theory
        public void yieldsDeserializerForAnyKindOfTinyType(Class<?> tinyType) throws JsonMappingException {
            final JavaType tinyTypeType = TypeFactory.defaultInstance().uncheckedSimpleType(tinyType);
            final JsonDeserializer<?> got = new TinyTypesDeserializers().findBeanDeserializer(tinyTypeType, null, null);
            Assert.assertNotNull(got);

        }
    }

    @RunWith(Theories.class)
    public static class DeserializerConstructor {

        @DataPoints
        public static final Class<?>[] tinyTypesSamples = new Class<?>[]{
            Samples.Str.class,
            Samples.Boolean.class,
            Samples.Byte.class,
            Samples.Short.class,
            Samples.Integer.class,
            Samples.Long.class};

        @Theory
        public void yieldsDeserializerForTinyType(Class<?> tinyType) {
            final TinyTypesDeserializer<?> deserializer = new TinyTypesDeserializer<>(tinyType);
            Assert.assertThat(deserializer, is(instanceOf(TinyTypesDeserializer.class)));
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNonTT() {
            new TinyTypesDeserializer<>(Object.class);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNull() {
            new TinyTypesDeserializer<>(null);
        }

    }

    @RunWith(Theories.class)
    public static class Deserialize {

        @DataPoints
        public static final Pair<String, Object>[] serializedAndExpectedDeserialization = new Pair[]{
            new Pair("\"a\"", new Samples.Str("a")),
            new Pair("true", new Samples.Boolean(true)),
            new Pair("1", new Samples.Byte((byte) 1)),
            new Pair("1", new Samples.Short((short) 1)),
            new Pair("1", new Samples.Integer(1)),
            new Pair("1", new Samples.Long(1))};

        @Theory
        public void objectMapperWithTTDeserializerInstalledYieldsExpectedDeserializationsForTTs(Pair<String, Object> serializedAndExpectedDeserialization) throws IOException {
            final ObjectMapper om = new ObjectMapper();
            om.registerModule(new DeserializersOnlyModule());
            final String serialzied = serializedAndExpectedDeserialization.fst;
            final Object expected = serializedAndExpectedDeserialization.snd;
            final Object got = om.readValue(serialzied, expected.getClass());
            Assert.assertEquals(got, expected);
        }

        public static class DeserializersOnlyModule extends Module {

            @Override
            public String getModuleName() {
                return "TT deserializers only";
            }

            @Override
            public Version version() {
                return new Version(0, 0, 0, "SNAPSHOT", "org.anima", "tinytypes-jackson");
            }

            @Override
            public void setupModule(Module.SetupContext context) {
                context.addDeserializers(new TinyTypesDeserializers());
            }
        }

    }

}
