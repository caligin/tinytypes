package org.anima.tinytypes;

import org.junit.Test;
import org.junit.Assert;

public class IntTinyTypeTest {

    @Test
    public void isEqualToSameTypeWithSameValue() {
        final Samples.Integer lhs = Samples.Integer.of(1);
        final Samples.Integer rhs = Samples.Integer.of(1);
        Assert.assertEquals(lhs, rhs);
    }

    @Test
    public void isDifferentThanSameTypeWithDifferentValue() {
        final Samples.Integer lhs = Samples.Integer.of(1);
        final Samples.Integer rhs = Samples.Integer.of(2);
        Assert.assertNotEquals(lhs, rhs);
    }

    @Test
    public void isDifferentThanDifferentSiblingTypeWithSameValue() {
        final Samples.Integer lhs = Samples.Integer.of(1);
        final Samples.OtherInteger rhs = Samples.OtherInteger.of(1);
        Assert.assertNotEquals(lhs, rhs);
    }

    @Test
    public void toStringContainsTTName() {
        final String expected = "Integer";
        final String toString = Samples.Integer.of(1).toString();
        Assert.assertTrue(toString.contains(expected));
    }

    @Test
    public void toStringContainsTTValue() {
        final String expected = "1";
        final String toString = Samples.Integer.of(1).toString();
        Assert.assertTrue(toString.contains(expected));
    }

}
