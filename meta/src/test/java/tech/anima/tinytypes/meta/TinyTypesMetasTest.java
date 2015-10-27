package tech.anima.tinytypes.meta;

import tech.anima.tinytypes.BooleanTinyType;
import tech.anima.tinytypes.ByteTinyType;
import tech.anima.tinytypes.IntTinyType;
import tech.anima.tinytypes.LongTinyType;
import tech.anima.tinytypes.ShortTinyType;
import tech.anima.tinytypes.StringTinyType;
import tech.anima.tinytypes.Samples;
import org.junit.Test;
import org.junit.Assert;

public class TinyTypesMetasTest {

    @Test(expected = IllegalArgumentException.class)
    public void forStringThrowsWhenNotATT() {
        MetaTinyTypes.metaFor(Object.class);
    }

    @Test
    public void forStringTTYieldsStringTTMeta() {
        final MetaTinyType<StringTinyType> got = MetaTinyTypes.metaFor(Samples.Str.class);
        Assert.assertTrue(got instanceof StringTinyTypes);
    }

    @Test
    public void forBooleanTTYieldsBooleanTTMeta() {
        final MetaTinyType<BooleanTinyType> got = MetaTinyTypes.metaFor(Samples.Boolean.class);
        Assert.assertTrue(got instanceof BooleanTinyTypes);
    }

    @Test
    public void forByteTTYieldsByteTTMeta() {
        final MetaTinyType<ByteTinyType> got = MetaTinyTypes.metaFor(Samples.Byte.class);
        Assert.assertTrue(got instanceof ByteTinyTypes);
    }

    @Test
    public void forShortTTYieldsShortTTMeta() {
        final MetaTinyType<ShortTinyType> got = MetaTinyTypes.metaFor(Samples.Short.class);
        Assert.assertTrue(got instanceof ShortTinyTypes);
    }

    @Test
    public void forIntTTYieldsIntTTMeta() {
        final MetaTinyType<IntTinyType> got = MetaTinyTypes.metaFor(Samples.Integer.class);
        Assert.assertTrue(got instanceof IntTinyTypes);
    }

    @Test
    public void forLongTTYieldsLongTTMeta() {
        final MetaTinyType<LongTinyType> got = MetaTinyTypes.metaFor(Samples.Long.class);
        Assert.assertTrue(got instanceof LongTinyTypes);
    }

}
