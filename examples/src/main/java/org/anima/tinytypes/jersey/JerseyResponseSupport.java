package org.anima.tinytypes.jersey;

import java.lang.reflect.Field;
import java.util.Set;
import javax.ws.rs.ext.RuntimeDelegate;
import org.anima.tinytypes.meta.BooleanTinyTypes;
import org.anima.tinytypes.meta.ByteTinyTypes;
import org.anima.tinytypes.meta.IntTinyTypes;
import org.anima.tinytypes.meta.LongTinyTypes;
import org.anima.tinytypes.meta.ShortTinyTypes;
import org.anima.tinytypes.meta.StringTinyTypes;
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

    private static void register(Class<?> candidate, final Set<HeaderDelegateProvider> systemRegisteredHeaderProviders) {
        if (StringTinyTypes.includes(candidate)) {
            systemRegisteredHeaderProviders.add(new StringTTHeaderDelegateProvider(candidate));
            return;
        }
        if (IntTinyTypes.includes(candidate)) {
            systemRegisteredHeaderProviders.add(new IntTTHeaderDelegateProvider(candidate));
            return;
        }
        if (ByteTinyTypes.includes(candidate)) {
            systemRegisteredHeaderProviders.add(new ByteTTHeaderDelegateProvider(candidate));
            return;
        }
        if (ShortTinyTypes.includes(candidate)) {
            systemRegisteredHeaderProviders.add(new ShortTTHeaderDelegateProvider(candidate));
            return;
        }
        if (LongTinyTypes.includes(candidate)) {
            systemRegisteredHeaderProviders.add(new LongTTHeaderDelegateProvider(candidate));
            return;
        }
        if (BooleanTinyTypes.includes(candidate)) {
            systemRegisteredHeaderProviders.add(new BooleanTTHeaderDelegateProvider(candidate));
            return;
        }
        throw new IllegalArgumentException(String.format("Not a supported TinyType: %s", candidate.getCanonicalName()));
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
