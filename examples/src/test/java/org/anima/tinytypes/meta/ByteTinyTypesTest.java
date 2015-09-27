package org.anima.tinytypes.meta;

import org.anima.tinytypes.examples.Samples;
import org.junit.Test;
import org.junit.Assert;

public class ByteTinyTypesTest {

    @Test
    public void includesYieldsTrueWhenCandidateSuperclassIsByteTT() {
        final boolean got = ByteTinyTypes.includes(Samples.Byte.class);
        Assert.assertTrue(got);
    }

    @Test
    public void includesYieldsTrueWhenCandidateSuperclassIsNotByteTT() {
        final boolean got = ByteTinyTypes.includes(Samples.class);
        Assert.assertFalse(got);
    }

    @Test
    public void includesYieldsTrueWhenAncestorOfCandidateIsByteTTButNotDirectSuperclass() {
        final boolean got = ByteTinyTypes.includes(ByteUndirectAncestor.class);
        Assert.assertFalse(got);
    }

    public static class ByteUndirectAncestor extends Samples.Byte {

        public ByteUndirectAncestor(byte value) {
            super(value);
        }
    }

}
