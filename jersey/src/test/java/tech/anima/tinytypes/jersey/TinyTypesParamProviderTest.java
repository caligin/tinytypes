package tech.anima.tinytypes.jersey;

import javax.ws.rs.ext.ParamConverter;
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
public class TinyTypesParamProviderTest {

    @RunWith(Theories.class)
    public static class GetConverter {

        @Test
        public void yieldsNullWhenTypeIsNotATinyType() {
            final Class<?> type = null;
            final ParamConverter<?> got = new TinyTypesParamProvider().getConverter(type, null, null);
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
        public void yieldsParamConverterForAnyKindOfTinyType(Class<?> tinyType) {
            final ParamConverter<?> got = new TinyTypesParamProvider().getConverter(tinyType, null, null);
            Assert.assertNotNull(got);

        }
    }

    @RunWith(Theories.class)
    public static class TTParamConverterConstructor {

        @DataPoints
        public static final Class<?>[] tinyTypesSamples = new Class<?>[]{
            Samples.Str.class,
            Samples.Boolean.class,
            Samples.Byte.class,
            Samples.Short.class,
            Samples.Integer.class,
            Samples.Long.class};

        @Theory
        public void yieldsParamConverterForTinyType(Class<?> tinyType) {
            final TinyTypesParamProvider.TinyTypesParamConverter<?> converter = new TinyTypesParamProvider.TinyTypesParamConverter<>(tinyType);
            Assert.assertThat(converter, is(instanceOf(TinyTypesParamProvider.TinyTypesParamConverter.class)));
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNonTT() {
            new TinyTypesParamProvider.TinyTypesParamConverter(Object.class);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNull() {
            new TinyTypesParamProvider.TinyTypesParamConverter(null);
        }

    }

    @RunWith(Theories.class)
    public static class ToString {

        @DataPoints
        public static final Pair<Object, String>[] ttAndExpectedStringification = new Pair[]{
            new Pair(new Samples.Str("a"), "a"),
            new Pair(new Samples.Boolean(true), "true"),
            new Pair(new Samples.Byte((byte) 1), "1"),
            new Pair(new Samples.Short((short) 1), "1"),
            new Pair(new Samples.Integer(1), "1"),
            new Pair(new Samples.Long(1), "1")};

        @Theory
        public void yieldsStringificationOfTT(Pair<Object, String> ttAndExpectedStringification) {
            final TinyTypesParamProvider.TinyTypesParamConverter paramConverter = new TinyTypesParamProvider.TinyTypesParamConverter(ttAndExpectedStringification.fst.getClass());
            final String expected = ttAndExpectedStringification.snd;
            final String got = paramConverter.toString(ttAndExpectedStringification.fst);
            Assert.assertEquals(expected, got);

        }
    }

    @RunWith(Theories.class)
    public static class FomString {

        @DataPoints
        public static final Pair<String, Object>[] stringificationAndExpectedTT = new Pair[]{
            new Pair("a", new Samples.Str("a")),
            new Pair("true", new Samples.Boolean(true)),
            new Pair("1", new Samples.Byte((byte) 1)),
            new Pair("1", new Samples.Short((short) 1)),
            new Pair("1", new Samples.Integer(1)),
            new Pair("1", new Samples.Long(1))};

        @Theory
        public void yieldsStringificationOfTT(Pair<String, Object> stringificationAndExpectedTT) {
            final TinyTypesParamProvider.TinyTypesParamConverter paramConverter = new TinyTypesParamProvider.TinyTypesParamConverter(stringificationAndExpectedTT.snd.getClass());
            final Object expected = stringificationAndExpectedTT.snd;
            final Object got = paramConverter.fromString(stringificationAndExpectedTT.fst);
            Assert.assertEquals(expected, got);

        }
    }

}
