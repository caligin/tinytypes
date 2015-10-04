package org.anima.tinytypes.jersey;

import org.anima.tinytypes.Samples;
import org.junit.Test;
import org.junit.Assert;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class TinyTypesHeaderDelegateProviderTest {

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
