package org.anima.tinytypes.meta;

public abstract class MetaTinyTypes {

    public static final MetaTinyType[] metas = new MetaTinyType[]{
        new StringTinyTypes(),
        new BooleanTinyTypes(),
        new ByteTinyTypes(),
        new ShortTinyTypes(),
        new IntTinyTypes(),
        new LongTinyTypes()
    };

    public static <T> MetaTinyType<T> metaFor(Class<?> candidate) {
        for (MetaTinyType meta : metas) {
            if (meta.isMetaOf(candidate)) {
                return meta;
            }
        }
        throw new IllegalArgumentException(String.format("not a tinytype: %s", candidate.getCanonicalName()));
    }

    public static boolean isTinyType(Class<?> candidate) {
        for (MetaTinyType meta : metas) {
            if (meta.isMetaOf(candidate)) {
                return true;
            }
        }
        return false;
    }

}
