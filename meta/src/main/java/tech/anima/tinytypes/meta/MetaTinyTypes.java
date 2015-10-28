package tech.anima.tinytypes.meta;

/**
 * Meta-support class for TinyTypes.
 */
public abstract class MetaTinyTypes {

    public static final MetaTinyType[] metas = new MetaTinyType[]{
        new StringTinyTypes(),
        new BooleanTinyTypes(),
        new ByteTinyTypes(),
        new ShortTinyTypes(),
        new IntTinyTypes(),
        new LongTinyTypes()
    };

    /**
     * Provides a type-specific Meta class for the given TinyType.
     *
     * @param <T> the TinyType class type
     * @param candidate the TinyType class to obtain a Meta for
     * @return a Meta implementation suitable for the candidate
     * @throws IllegalArgumentException for null or a non-TinyType
     */
    public static <T> MetaTinyType<T> metaFor(Class<?> candidate) {
        for (MetaTinyType meta : metas) {
            if (meta.isMetaOf(candidate)) {
                return meta;
            }
        }
        throw new IllegalArgumentException(String.format("not a tinytype: %s", candidate == null ? "null" : candidate.getCanonicalName()));
    }

    /**
     * Checks whether a class is a TinyType. A class is considered a TinyType if
     * is a direct ancestor of a tech.anima.tinytypes.*TinyType, is not abstract
     * and provides a ctor matching super.
     *
     * @param candidate the class to be checked
     * @return true if the candidate is a TinyType, false otherwise.
     */
    public static boolean isTinyType(Class<?> candidate) {
        for (MetaTinyType meta : metas) {
            if (meta.isMetaOf(candidate)) {
                return true;
            }
        }
        return false;
    }

}
