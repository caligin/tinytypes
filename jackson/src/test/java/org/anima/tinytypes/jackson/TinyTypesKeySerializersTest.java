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
import org.junit.Test;
import org.junit.Assert;
import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class TinyTypesKeySerializersTest {

    @RunWith(Theories.class)
    public static class FindKeySerializer {

        @Test
        public void delegatesToOtherWhenTypeIsNotATinyType() throws JsonMappingException {
            final JavaType typeForObject = TypeFactory.defaultInstance().uncheckedSimpleType(Object.class);
            final JsonSerializer<?> got = new TinyTypesKeySerializers().findSerializer(null, typeForObject, null);
            Assert.assertFalse(got instanceof TinyTypesKeySerializers.TinyTypesKeySerializer);
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
        public void yieldsKeySerializerForAnyKindOfTinyType(Class<?> tinyType) throws JsonMappingException {
            final JavaType tinyTypeType = TypeFactory.defaultInstance().uncheckedSimpleType(tinyType);
            final JsonSerializer<?> got = new TinyTypesKeySerializers().findSerializer(null, tinyTypeType, null);
            Assert.assertTrue(got instanceof TinyTypesKeySerializers.TinyTypesKeySerializer);

        }
    }

    @RunWith(Theories.class)
    public static class KeySerializerConstructor {

        @DataPoints
        public static final Class<?>[] tinyTypesSamples = new Class<?>[]{
            Samples.Str.class,
            Samples.Boolean.class,
            Samples.Byte.class,
            Samples.Short.class,
            Samples.Integer.class,
            Samples.Long.class};

        @Theory
        public void yieldsKeySerializerForTinyType(Class<?> tinyType) {
            final TinyTypesKeySerializers.TinyTypesKeySerializer serializer = new TinyTypesKeySerializers.TinyTypesKeySerializer(tinyType);
            Assert.assertThat(serializer, is(instanceOf(TinyTypesKeySerializers.TinyTypesKeySerializer.class)));
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNonTT() {
            new TinyTypesKeySerializers.TinyTypesKeySerializer(Object.class);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNull() {
            new TinyTypesKeySerializers.TinyTypesKeySerializer(null);
        }

    }

    public static class Serialize {

        @Test
        public void objectMapperWithTTKeySerializerInstalledYieldsExpectedSerializationsForStringTT() throws IOException {
            final ObjectMapper om = new ObjectMapper();
            om.registerModule(new KeySerializersOnlyModule());
            final ReifiedStrKeyMap toSerialzie = new ReifiedStrKeyMap((Map) Collections.singletonMap(new Samples.Str("a"), null));
            final String expected = asJsonMapKey("\"a\"");
            final String got = om.writeValueAsString(toSerialzie);
            Assert.assertEquals(expected, got);
        }

        @Test
        public void objectMapperWithTTKeySerializerInstalledYieldsExpectedSerializationsForBooleanTT() throws IOException {
            final ObjectMapper om = new ObjectMapper();
            om.registerModule(new KeySerializersOnlyModule());
            final ReifiedBooleanKeyMap toSerialzie = new ReifiedBooleanKeyMap((Map) Collections.singletonMap(new Samples.Boolean(true), null));
            final String expected = asJsonMapKey("\"true\"");
            final String got = om.writeValueAsString(toSerialzie);
            Assert.assertEquals(expected, got);
        }

        @Test
        public void objectMapperWithTTKeySerializerInstalledYieldsExpectedSerializationsForByteTT() throws IOException {
            final ObjectMapper om = new ObjectMapper();
            om.registerModule(new KeySerializersOnlyModule());
            final ReifiedByteKeyMap toSerialzie = new ReifiedByteKeyMap((Map) Collections.singletonMap(new Samples.Byte((byte) 1), null));
            final String expected = asJsonMapKey("\"1\"");
            final String got = om.writeValueAsString(toSerialzie);
            Assert.assertEquals(expected, got);
        }

        @Test
        public void objectMapperWithShortTTKeySerializerInstalledYieldsExpectedSerializationsForShortTT() throws IOException {
            final ObjectMapper om = new ObjectMapper();
            om.registerModule(new KeySerializersOnlyModule());
            final ReifiedShortKeyMap toSerialzie = new ReifiedShortKeyMap((Map) Collections.singletonMap(new Samples.Short((short) 1), null));
            final String expected = asJsonMapKey("\"1\"");
            final String got = om.writeValueAsString(toSerialzie);
            Assert.assertEquals(expected, got);
        }

        @Test
        public void objectMapperWithTTKeySerializerInstalledYieldsExpectedSerializationsForIntTT() throws IOException {
            final ObjectMapper om = new ObjectMapper();
            om.registerModule(new KeySerializersOnlyModule());
            final ReifiedIntKeyMap toSerialzie = new ReifiedIntKeyMap((Map) Collections.singletonMap(new Samples.Integer(1), null));
            final String expected = asJsonMapKey("\"1\"");
            final String got = om.writeValueAsString(toSerialzie);
            Assert.assertEquals(expected, got);
        }

        @Test
        public void objectMapperWithTTKeySerializerInstalledYieldsExpectedSerializationsForLongTT() throws IOException {
            final ObjectMapper om = new ObjectMapper();
            om.registerModule(new KeySerializersOnlyModule());
            final ReifiedLongKeyMap toSerialzie = new ReifiedLongKeyMap((Map) Collections.singletonMap(new Samples.Long(1), null));
            final String expected = asJsonMapKey("\"1\"");
            final String got = om.writeValueAsString(toSerialzie);
            Assert.assertEquals(expected, got);
        }

        public static class ReifiedStrKeyMap extends HashMap<Samples.Str, Void> {

            public ReifiedStrKeyMap(Map<? extends Samples.Str, ? extends Void> m) {
                super(m);
            }
        }

        public static class ReifiedBooleanKeyMap extends HashMap<Samples.Boolean, Void> {

            public ReifiedBooleanKeyMap(Map<? extends Samples.Boolean, ? extends Void> m) {
                super(m);
            }
        }

        public static class ReifiedByteKeyMap extends HashMap<Samples.Byte, Void> {

            public ReifiedByteKeyMap(Map<? extends Samples.Byte, ? extends Void> m) {
                super(m);
            }
        }

        public static class ReifiedShortKeyMap extends HashMap<Samples.Short, Void> {

            public ReifiedShortKeyMap(Map<? extends Samples.Short, ? extends Void> m) {
                super(m);
            }
        }

        public static class ReifiedIntKeyMap extends HashMap<Samples.Integer, Void> {

            public ReifiedIntKeyMap(Map<? extends Samples.Integer, ? extends Void> m) {
                super(m);
            }
        }

        public static class ReifiedLongKeyMap extends HashMap<Samples.Long, Void> {

            public ReifiedLongKeyMap(Map<? extends Samples.Long, ? extends Void> m) {
                super(m);
            }
        }

        private String asJsonMapKey(String key) {
            return String.format("{%s:null}", key);
        }

        public static class KeySerializersOnlyModule extends Module {

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
                context.addKeySerializers(new TinyTypesKeySerializers());
            }
        }

    }

}
