package tech.anima.tinytypes.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import tech.anima.tinytypes.Samples;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class TinyTypesKeyDeserializersTest {

    @RunWith(Theories.class)
    public static class FindKeyDeserializer {

        @Test
        public void yieldsNullWhenTypeIsNotATinyType() throws JsonMappingException {
            final JavaType typeForObject = TypeFactory.defaultInstance().uncheckedSimpleType(Object.class);
            final KeyDeserializer got = new TinyTypesKeyDeserializers().findKeyDeserializer(typeForObject, null, null);
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
        public void yieldsKeyDeserializerForAnyKindOfTinyType(Class<?> tinyType) throws JsonMappingException {
            final JavaType tinyTypeType = TypeFactory.defaultInstance().uncheckedSimpleType(tinyType);
            final KeyDeserializer got = new TinyTypesKeyDeserializers().findKeyDeserializer(tinyTypeType, null, null);
            Assert.assertNotNull(got);

        }
    }

    @RunWith(Theories.class)
    public static class KeyDeserializerConstructor {

        @DataPoints
        public static final Class<?>[] tinyTypesSamples = new Class<?>[]{
            Samples.Str.class,
            Samples.Boolean.class,
            Samples.Byte.class,
            Samples.Short.class,
            Samples.Integer.class,
            Samples.Long.class};

        @Theory
        public void yieldsKeyDeserializerForTinyType(Class<?> tinyType) {
            final TinyTypesKeyDeserializers.TinyTypesKeyDeserializer deserializer = new TinyTypesKeyDeserializers.TinyTypesKeyDeserializer(tinyType);
            Assert.assertThat(deserializer, is(instanceOf(TinyTypesKeyDeserializers.TinyTypesKeyDeserializer.class)));
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNonTT() {
            new TinyTypesKeyDeserializers.TinyTypesKeyDeserializer(Object.class);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNull() {
            new TinyTypesKeyDeserializers.TinyTypesKeyDeserializer(null);
        }

    }

    @RunWith(Theories.class)
    public static class DeserializeKey {

        @DataPoints
        public static final Pair<String, Object>[] serializedAndExpectedDeserialization = new Pair[]{
            new Pair("\"a\"", new Samples.Str("a")),
            new Pair("\"true\"", new Samples.Boolean(true)),
            new Pair("\"1\"", new Samples.Byte((byte) 1)),
            new Pair("\"1\"", new Samples.Short((short) 1)),
            new Pair("\"1\"", new Samples.Integer(1)),
            new Pair("\"1\"", new Samples.Long(1))};

        @Theory
        public void objectMapperWithTTKeyDeserializerInstalledYieldsExpectedDeserializationsForTTs(Pair<String, Object> serializedAndExpectedDeserialization) throws IOException {
            final ObjectMapper om = new ObjectMapper();
            om.registerModule(new KeyDeserializersOnlyModule());
            final String serialzied = serializedAndExpectedDeserialization.fst;
            final Object expected = serializedAndExpectedDeserialization.snd;
            final JavaType mapType = MapType.construct(HashMap.class, SimpleType.construct(serializedAndExpectedDeserialization.snd.getClass()), SimpleType.construct(Void.class));
            final Object got = ((Map)om.readValue(asJsonMapKey(serialzied), mapType)).keySet().iterator().next();
            Assert.assertEquals(got, expected);
        }
        
        private String asJsonMapKey(String key) {
            return String.format("{%s:null}", key);
        }

        public static class KeyDeserializersOnlyModule extends Module {

            @Override
            public String getModuleName() {
                return "TT key deserializers only";
            }

            @Override
            public Version version() {
                return new Version(0, 0, 0, "SNAPSHOT", "tech.anima", "tinytypes-jackson");
            }

            @Override
            public void setupModule(Module.SetupContext context) {
                context.addKeyDeserializers(new TinyTypesKeyDeserializers());
            }
        }

    }

}
