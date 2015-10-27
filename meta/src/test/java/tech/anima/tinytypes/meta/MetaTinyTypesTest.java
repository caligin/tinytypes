package tech.anima.tinytypes.meta;

import tech.anima.tinytypes.Samples;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class MetaTinyTypesTest {

    @RunWith(Theories.class)
    public static class IsTinyType {

        @DataPoints("tts")
        public static final Class<?>[] tinyTypesSamples = new Class<?>[]{
            Samples.Str.class,
            Samples.Boolean.class,
            Samples.Byte.class,
            Samples.Short.class,
            Samples.Integer.class,
            Samples.Long.class};

        @Theory
        public void classesThatExtendATTKindAreTTs(@FromDataPoints("tts") Class<?> tinyType) {
            Assert.assertTrue(MetaTinyTypes.isTinyType(tinyType));
        }

        @DataPoints("extend-tts")
        public static final Class<?>[] sampelsExtendingATinyType = new Class<?>[]{
            Samples.StrIndirectAncestor.class,
            Samples.BooleanIndirectAncestor.class,
            Samples.ByteIndirectAncestor.class,
            Samples.ShortIndirectAncestor.class,
            Samples.IntIndirectAncestor.class,
            Samples.LongIndirectAncestor.class};

        @Theory
        public void classesThatExtendATTAreNotTTs(@FromDataPoints("extend-tts") Class<?> tinyType) {
            Assert.assertFalse(MetaTinyTypes.isTinyType(tinyType));
        }

        @Test
        public void classUnrelatedToTTsIsNotTT() {
            Assert.assertFalse(MetaTinyTypes.isTinyType(Object.class));
        }

        @Test
        public void nullIsNotATT() {
            Assert.assertFalse(MetaTinyTypes.isTinyType(null));
        }
    }

    @RunWith(Theories.class)
    public static class MetaFor {

        @DataPoints
        public static final Pair<Class<?>, Class<MetaTinyType>>[] ttsAndExpectedMetas = new Pair[]{
            new Pair(Samples.Str.class, StringTinyTypes.class),
            new Pair(Samples.Boolean.class, BooleanTinyTypes.class),
            new Pair(Samples.Byte.class, ByteTinyTypes.class),
            new Pair(Samples.Short.class, ShortTinyTypes.class),
            new Pair(Samples.Integer.class, IntTinyTypes.class),
            new Pair(Samples.Long.class, LongTinyTypes.class)};

        @Theory
        public void yieldsCorrectMetaForAnyKindOfTinyType(Pair<Class<?>, Class<MetaTinyType>> ttAndMeta) {
            final Class<?> tt = ttAndMeta.fst;
            final MetaTinyType<?> got = MetaTinyTypes.metaFor(tt);
            final Class<?> expected = ttAndMeta.snd;
            Assert.assertEquals(expected, got.getClass());
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsWhenNotATT() {
            MetaTinyTypes.metaFor(Object.class);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsOnNull() {
            MetaTinyTypes.metaFor(null);
        }
    }

}
