package tech.anima.tinytypes;

import org.junit.Test;
import org.junit.Assert;

public class ShortTinyTypeTest {

    @Test
    public void isEqualToSameTypeWithSameValue() {
        final Samples.Short lhs = Samples.Short.of((short) 1);
        final Samples.Short rhs = Samples.Short.of((short) 1);
        Assert.assertEquals(lhs, rhs);
    }

    @Test
    public void isDifferentThanSameTypeWithDifferentValue() {
        final Samples.Short lhs = Samples.Short.of((short) 1);
        final Samples.Short rhs = Samples.Short.of((short) 2);
        Assert.assertNotEquals(lhs, rhs);
    }

    @Test
    public void isDifferentThanDifferentSiblingTypeWithSameValue() {
        final Samples.Short lhs = Samples.Short.of((short) 1);
        final Samples.OtherShort rhs = Samples.OtherShort.of((short) 1);
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
