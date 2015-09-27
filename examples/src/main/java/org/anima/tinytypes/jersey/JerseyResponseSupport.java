package org.anima.tinytypes.jersey;

import java.lang.reflect.Field;
import java.util.Set;
import javax.ws.rs.ext.RuntimeDelegate;
import org.anima.tinytypes.ByteTinyType;
import org.anima.tinytypes.IntTinyType;
import org.anima.tinytypes.LongTinyType;
import org.anima.tinytypes.ShortTinyType;
import org.anima.tinytypes.StringTinyType;
import org.glassfish.jersey.internal.AbstractRuntimeDelegate;
import org.glassfish.jersey.server.internal.RuntimeDelegateImpl;
import org.glassfish.jersey.spi.HeaderDelegateProvider;

public class JerseyResponseSupport {

    public static void registerTinyTypes(Class<?> head, Class<?>... tail) {
        final Set<HeaderDelegateProvider> systemRegisteredHeaderProviders = stealAcquireRefToHeaderDelegateProviders();
        register(head, systemRegisteredHeaderProviders);
        for (Class<?> tt : tail) {
            register(tt, systemRegisteredHeaderProviders);
        }
    }

    private static void register(Class<?> tt, final Set<HeaderDelegateProvider> systemRegisteredHeaderProviders) {
        final Class<?> kind = tt.getSuperclass();
        if (StringTinyType.class.equals(kind)) {
            systemRegisteredHeaderProviders.add(new StringTTHeaderDelegateProvider(tt));
            return;
        }
        if (IntTinyType.class.equals(kind)) {
            systemRegisteredHeaderProviders.add(new IntTTHeaderDelegateProvider(tt));
            return;
        }
        if (ByteTinyType.class.equals(kind)) {
            systemRegisteredHeaderProviders.add(new ByteTTHeaderDelegateProvider(tt));
            return;
        }
        if (ShortTinyType.class.equals(kind)) {
            systemRegisteredHeaderProviders.add(new ShortTTHeaderDelegateProvider(tt));
            return;
        }
        if (LongTinyType.class.equals(kind)) {
            systemRegisteredHeaderProviders.add(new LongTTHeaderDelegateProvider(tt));
            return;
        }
        throw new IllegalArgumentException(String.format("Not a supported TinyType: %s", tt.getCanonicalName()));
    }

    private static Set<HeaderDelegateProvider> stealAcquireRefToHeaderDelegateProviders() {
        try {
            final RuntimeDelegate instance = RuntimeDelegate.getInstance();
            final RuntimeDelegateImpl s = (RuntimeDelegateImpl) instance;
            final Field declaredField;
            declaredField = AbstractRuntimeDelegate.class.getDeclaredField("hps");
            declaredField.setAccessible(true);
            final Set<HeaderDelegateProvider> get = (Set<HeaderDelegateProvider>) declaredField.get(s);
            return get;
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException ex) {
            throw new RuntimeException(ex);
        }
    }

}
