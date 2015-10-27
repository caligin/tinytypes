package tech.anima.tinytypes.jersey;

import tech.anima.tinytypes.Samples;
import org.junit.Test;
import org.junit.Assert;
import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class TinyTypesHeaderDelegateProviderTest {

    @RunWith(Theories.class)
    public static class Ctor {

        @DataPoints
        public static final Class<?>[] tinyTypesSamples = new Class<?>[]{
            Samples.Str.class,
            Samples.Boolean.class,
            Samples.Byte.class,
            Samples.Short.class,
            Samples.Integer.class,
            Samples.Long.class};

        @Theory
        public void yieldsHeaderDelegateProviderForTinyTypes(Class<?> tt) {
            final TinyTypesHeaderDelegateProvider<?> hdp = new TinyTypesHeaderDelegateProvider<>(tt);
            Assert.assertTrue(hdp instanceof TinyTypesHeaderDelegateProvider);

        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNull() {
            new TinyTypesHeaderDelegateProvider<>(null);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNonTT() {
            new TinyTypesHeaderDelegateProvider<>(Object.class);
        }
    }

    public static class Supports {

        @Test
        public void yieldsTrueForSameTinyType() {
            Assert.assertTrue(new TinyTypesHeaderDelegateProvider<>(Samples.Str.class).supports(Samples.Str.class));
        }

        @Test
        public void yieldsFalseForDifferentTinyType() {
            Assert.assertFalse(new TinyTypesHeaderDelegateProvider<>(Samples.Str.class).supports(Samples.Integer.class));
        }

        @Test
        public void yieldsFalseForNull() {
            Assert.assertFalse(new TinyTypesHeaderDelegateProvider<>(Samples.Str.class).supports(null));
        }

        @Test
        public void yieldsFalseForNonTT() {
            Assert.assertFalse(new TinyTypesHeaderDelegateProvider<>(Samples.Str.class).supports(Object.class));
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
            final TinyTypesHeaderDelegateProvider hdp = new TinyTypesHeaderDelegateProvider<>(ttAndExpectedStringification.fst.getClass());
            final String expected = ttAndExpectedStringification.snd;
            final String got = hdp.toString(ttAndExpectedStringification.fst);
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
            final TinyTypesHeaderDelegateProvider hdp = new TinyTypesHeaderDelegateProvider<>(stringificationAndExpectedTT.snd.getClass());
            final Object expected = stringificationAndExpectedTT.snd;
            final Object got = hdp.fromString(stringificationAndExpectedTT.fst);
            Assert.assertEquals(expected, got);

        }
    }

    public static class HashCode {

        @Test
        public void isSameForSameType() {
            final TinyTypesHeaderDelegateProvider<Samples.Long> longHDP1 = new TinyTypesHeaderDelegateProvider<>(Samples.Long.class);
            final TinyTypesHeaderDelegateProvider<Samples.Long> longHDP2 = new TinyTypesHeaderDelegateProvider<>(Samples.Long.class);
            Assert.assertEquals(longHDP1.hashCode(), longHDP2.hashCode());
        }

        @Test
        public void isDifferentForDifferentType() {
            final TinyTypesHeaderDelegateProvider<Samples.Long> longHDP = new TinyTypesHeaderDelegateProvider<>(Samples.Long.class);
            final TinyTypesHeaderDelegateProvider<Samples.Integer> intHDP = new TinyTypesHeaderDelegateProvider<>(Samples.Integer.class);
            Assert.assertNotEquals(longHDP.hashCode(), intHDP.hashCode());
        }
    }

    public static class Equals {

        @Test
        public void twoHDPAreEqualsWhenTypeIsEqual() {
            final TinyTypesHeaderDelegateProvider<Samples.Long> longHDP1 = new TinyTypesHeaderDelegateProvider<>(Samples.Long.class);
            final TinyTypesHeaderDelegateProvider<Samples.Long> longHDP2 = new TinyTypesHeaderDelegateProvider<>(Samples.Long.class);
            Assert.assertEquals(longHDP1, longHDP2);
        }

        @Test
        public void twoHDPAreNotEqualsWhenTypeIsDifferent() {
            final TinyTypesHeaderDelegateProvider<Samples.Long> longHDP = new TinyTypesHeaderDelegateProvider<>(Samples.Long.class);
            final TinyTypesHeaderDelegateProvider<Samples.Integer> intHDP = new TinyTypesHeaderDelegateProvider<>(Samples.Integer.class);
            Assert.assertNotEquals(longHDP, intHDP);
        }

        @Test
        public void HDPIsNotEqualOtherThings() {
            final TinyTypesHeaderDelegateProvider<Samples.Long> longHDP = new TinyTypesHeaderDelegateProvider<>(Samples.Long.class);
            Assert.assertNotEquals(longHDP, new Object());
        }

        @Test
        public void HDPIsNotEqualNull() {
            final TinyTypesHeaderDelegateProvider<Samples.Long> longHDP = new TinyTypesHeaderDelegateProvider<>(Samples.Long.class);
            Assert.assertNotEquals(longHDP, null);
        }
    }

}
