package tech.anima.tinytypes;

import org.junit.Test;
import org.junit.Assert;

public class LongTinyTypeTest {

    @Test
    public void isEqualToSameTypeWithSameValue() {
        final Samples.Long lhs = Samples.Long.of(1);
        final Samples.Long rhs = Samples.Long.of(1);
        Assert.assertEquals(lhs, rhs);
    }

    @Test
    public void isDifferentThanSameTypeWithDifferentValue() {
        final Samples.Long lhs = Samples.Long.of(1);
        final Samples.Long rhs = Samples.Long.of(2);
        Assert.assertNotEquals(lhs, rhs);
    }

    @Test
    public void isDifferentThanDifferentSiblingTypeWithSameValue() {
        final Samples.Long lhs = Samples.Long.of(1);
        final Samples.OtherLong rhs = Samples.OtherLong.of(1);
        Assert.assertNotEquals(lhs, rhs);
    }

    @Test
    public void toStringContainsTTName() {
        final String expected = "Long";
        final String toString = Samples.Long.of(1).toString();
        Assert.assertTrue(toString.contains(expected));
    }

    @Test
    public void toStringContainsTTValue() {
        final String expected = "1";
        final String toString = Samples.Long.of(1).toString();
        Assert.assertTrue(toString.contains(expected));
    }

}
