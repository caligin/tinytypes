package org.anima.tinytypes;

import org.junit.Test;
import org.junit.Assert;

public class BooleanTinyTypeTest {

    @Test
    public void isEqualToSameTypeWithSameValue() {
        final Samples.Boolean lhs = Samples.Boolean.of(true);
        final Samples.Boolean rhs = Samples.Boolean.of(true);
        Assert.assertEquals(lhs, rhs);
    }

    @Test
    public void isDifferentThanSameTypeWithDifferentValue() {
        final Samples.Boolean lhs = Samples.Boolean.of(true);
        final Samples.Boolean rhs = Samples.Boolean.of(false);
        Assert.assertNotEquals(lhs, rhs);
    }

    @Test
    public void isDifferentThanDifferentSiblingTypeWithSameValue() {
        final Samples.Boolean lhs = Samples.Boolean.of(true);
        final Samples.OtherBoolean rhs = Samples.OtherBoolean.of(true);
        Assert.assertNotEquals(lhs, rhs);
    }
    @Test
    public void toStringContainsTTName() {
        final String expected = "Short";
        final String toString = Samples.Short.of((short) 1).toString();
        Assert.assertTrue(toString.contains(expected));
    }

    @Test
    public void toStringContainsTTValue() {
        final String expected = "1";
        final String toString = Samples.Short.of((short) 1).toString();
        Assert.assertTrue(toString.contains(expected));
    }

}
