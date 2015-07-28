package org.anima.tinytypes;

import org.junit.Test;
import org.junit.Assert;

public class ByteTinyTypeTest {

    @Test
    public void isEqualToSameTypeWithSameValue() {
        final Samples.Byte lhs = Samples.Byte.of((byte)1);
        final Samples.Byte rhs = Samples.Byte.of((byte)1);
        Assert.assertEquals(lhs, rhs);
    }

    @Test
    public void isDifferentThanSameTypeWithDifferentValue() {
        final Samples.Byte lhs = Samples.Byte.of((byte)1);
        final Samples.Byte rhs = Samples.Byte.of((byte)2);
        Assert.assertNotEquals(lhs, rhs);
    }

    @Test
    public void isDifferentThanDifferentSiblingTypeWithSameValue() {
        final Samples.Byte lhs = Samples.Byte.of((byte)1);
        final Samples.OtherByte rhs = Samples.OtherByte.of((byte)1);
        Assert.assertNotEquals(lhs, rhs);
    }

}
