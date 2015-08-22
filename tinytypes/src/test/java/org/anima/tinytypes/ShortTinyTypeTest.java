package org.anima.tinytypes;

import org.junit.Test;
import org.junit.Assert;

public class ShortTinyTypeTest {

    @Test
    public void isEqualToSameTypeWithSameValue() {
        final Samples.Short lhs = Samples.Short.of((short)1);
        final Samples.Short rhs = Samples.Short.of((short)1);
        Assert.assertEquals(lhs, rhs);
    }

    @Test
    public void isDifferentThanSameTypeWithDifferentValue() {
        final Samples.Short lhs = Samples.Short.of((short)1);
        final Samples.Short rhs = Samples.Short.of((short)2);
        Assert.assertNotEquals(lhs, rhs);
    }

    @Test
    public void isDifferentThanDifferentSiblingTypeWithSameValue() {
        final Samples.Short lhs = Samples.Short.of((short)1);
        final Samples.OtherShort rhs = Samples.OtherShort.of((short)1);
        Assert.assertNotEquals(lhs, rhs);
    }

}
