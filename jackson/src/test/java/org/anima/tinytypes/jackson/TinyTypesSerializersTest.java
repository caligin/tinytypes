package org.anima.tinytypes.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.anima.tinytypes.Samples;
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
public class TinyTypesSerializersTest {

    @RunWith(Theories.class)
    public static class FindSerializer {

        @Test
        public void delegatesToOtherWhenTypeIsNotATinyType() throws JsonMappingException {
            final JavaType typeForObject = TypeFactory.defaultInstance().uncheckedSimpleType(Object.class);
            final JsonSerializer<?> got = new TinyTypesSerializers().findSerializer(null, typeForObject, null);
            Assert.assertFalse(got instanceof TinyTypesSerializers.TinyTypesSerializer);
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
        public void yieldsSerializerForAnyKindOfTinyType(Class<?> tinyType) throws JsonMappingException {
            final JavaType tinyTypeType = TypeFactory.defaultInstance().uncheckedSimpleType(tinyType);
            final JsonSerializer<?> got = new TinyTypesSerializers().findSerializer(null, tinyTypeType, null);
            Assert.assertTrue(got instanceof TinyTypesSerializers.TinyTypesSerializer);

        }
    }

    @RunWith(Theories.class)
    public static class SerializerConstructor {

        @DataPoints
        public static final Class<?>[] tinyTypesSamples = new Class<?>[]{
            Samples.Str.class,
            Samples.Boolean.class,
            Samples.Byte.class,
            Samples.Short.class,
            Samples.Integer.class,
            Samples.Long.class};

        @Theory
        public void yieldsSerializerForTinyType(Class<?> tinyType) {
            final TinyTypesSerializers.TinyTypesSerializer serializer = new TinyTypesSerializers.TinyTypesSerializer(tinyType);
            Assert.assertThat(serializer, is(instanceOf(TinyTypesSerializers.TinyTypesSerializer.class)));
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNonTT() {
            new TinyTypesSerializers.TinyTypesSerializer(Object.class);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNull() {
            new TinyTypesSerializers.TinyTypesSerializer(null);
        }

    }

            @RunWith(Theories.class)
    public static class Serialize {

        

        @DataPoints
        public static final Pair<Object, String>[] ttAndExpectedSerialization = new Pair[]{
            new Pair(new Samples.Str("a"), "\"a\""),
            new Pair(new Samples.Boolean(true), "true"),
            new Pair(new Samples.Byte((byte) 1), "1"),
            new Pair(new Samples.Short((short) 1), "1"),
            new Pair(new Samples.Integer(1), "1"),
            new Pair(new Samples.Long(1), "1")};

        @Theory
        public void objectMapperWithTTDeserializerInstalledYieldsExpectedDeserializationsForTTs(Pair<Object, String> serializedAndExpectedDeserialization) throws IOException {
            final ObjectMapper om = new ObjectMapper();
            om.registerModule(new SerializersOnlyModule());
            final String expected = serializedAndExpectedDeserialization.snd;
            final Object got = om.writeValueAsString(serializedAndExpectedDeserialization.fst);
            Assert.assertEquals(expected, got);
        }

        
//        @Test
//        public void objectMapperWithTTSerializerInstalledYieldsExpectedSerializationsForStringTT() throws IOException {
//            final ObjectMapper om = new ObjectMapper();
//            om.registerModule(new SerializersOnlyModule());
//            final ReifiedStrMap toSerialzie = new ReifiedStrMap((Map) Collections.singletonMap(new Samples.Str("a"), null));
//            final String expected = asJsonMap("\"a\"");
//            final String got = om.writeValueAsString(toSerialzie);
//            Assert.assertEquals(expected, got);
//        }
//
//        @Test
//        public void objectMapperWithTTSerializerInstalledYieldsExpectedSerializationsForBooleanTT() throws IOException {
//            final ObjectMapper om = new ObjectMapper();
//            om.registerModule(new SerializersOnlyModule());
//            final ReifiedBooleanMap toSerialzie = new ReifiedBooleanMap((Map) Collections.singletonMap(new Samples.Boolean(true), null));
//            final String expected = asJsonMap("\"true\"");
//            final String got = om.writeValueAsString(toSerialzie);
//            Assert.assertEquals(expected, got);
//        }
//
//        @Test
//        public void objectMapperWithTTSerializerInstalledYieldsExpectedSerializationsForByteTT() throws IOException {
//            final ObjectMapper om = new ObjectMapper();
//            om.registerModule(new SerializersOnlyModule());
//            final ReifiedByteMap toSerialzie = new ReifiedByteMap((Map) Collections.singletonMap(new Samples.Byte((byte) 1), null));
//            final String expected = asJsonMap("\"1\"");
//            final String got = om.writeValueAsString(toSerialzie);
//            Assert.assertEquals(expected, got);
//        }
//
//        @Test
//        public void objectMapperWithShortTTSerializerInstalledYieldsExpectedSerializationsForShortTT() throws IOException {
//            final ObjectMapper om = new ObjectMapper();
//            om.registerModule(new SerializersOnlyModule());
//            final ReifiedShortMap toSerialzie = new ReifiedShortMap((Map) Collections.singletonMap(new Samples.Short((short) 1), null));
//            final String expected = asJsonMap("\"1\"");
//            final String got = om.writeValueAsString(toSerialzie);
//            Assert.assertEquals(expected, got);
//        }
//
//        @Test
//        public void objectMapperWithTTSerializerInstalledYieldsExpectedSerializationsForIntTT() throws IOException {
//            final ObjectMapper om = new ObjectMapper();
//            om.registerModule(new SerializersOnlyModule());
//            final ReifiedIntMap toSerialzie = new ReifiedIntMap((Map) Collections.singletonMap(new Samples.Integer(1), null));
//            final String expected = asJsonMap("\"1\"");
//            final String got = om.writeValueAsString(toSerialzie);
//            Assert.assertEquals(expected, got);
//        }
//
//        @Test
//        public void objectMapperWithTTSerializerInstalledYieldsExpectedSerializationsForLongTT() throws IOException {
//            final ObjectMapper om = new ObjectMapper();
//            om.registerModule(new SerializersOnlyModule());
//            final ReifiedLongMap toSerialzie = new ReifiedLongMap((Map) Collections.singletonMap(new Samples.Long(1), null));
//            final String expected = asJsonMap("\"1\"");
//            final String got = om.writeValueAsString(toSerialzie);
//            Assert.assertEquals(expected, got);
//        }
//
//        public static class ReifiedStrMap extends HashMap<Samples.Str, Void> {
//
//            public ReifiedStrMap(Map<? extends Samples.Str, ? extends Void> m) {
//                super(m);
//            }
//        }
//
//        public static class ReifiedBooleanMap extends HashMap<Samples.Boolean, Void> {
//
//            public ReifiedBooleanMap(Map<? extends Samples.Boolean, ? extends Void> m) {
//                super(m);
//            }
//        }
//
//        public static class ReifiedByteMap extends HashMap<Samples.Byte, Void> {
//
//            public ReifiedByteMap(Map<? extends Samples.Byte, ? extends Void> m) {
//                super(m);
//            }
//        }
//
//        public static class ReifiedShortMap extends HashMap<Samples.Short, Void> {
//
//            public ReifiedShortMap(Map<? extends Samples.Short, ? extends Void> m) {
//                super(m);
//            }
//        }
//
//        public static class ReifiedIntMap extends HashMap<Samples.Integer, Void> {
//
//            public ReifiedIntMap(Map<? extends Samples.Integer, ? extends Void> m) {
//                super(m);
//            }
//        }
//
//        public static class ReifiedLongMap extends HashMap<Samples.Long, Void> {
//
//            public ReifiedLongMap(Map<? extends Samples.Long, ? extends Void> m) {
//                super(m);
//            }
//        }
//
//        private String asJsonMap(String key) {
//            return String.format("{%s:null}", key);
//        }

        public static class SerializersOnlyModule extends Module {

            @Override
            public String getModuleName() {
                return "TT key serializers only";
            }

            @Override
            public Version version() {
                return new Version(0, 0, 0, "SNAPSHOT", "org.anima", "tinytypes-jackson");
            }

            @Override
            public void setupModule(Module.SetupContext context) {
                context.addSerializers(new TinyTypesSerializers());
            }
        }

    }

}
