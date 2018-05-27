package uk.co.alt236.bluetoothdebug.reflection;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public abstract class OverrideValidator<O, D> {
    private final String tag;

    protected OverrideValidator(final String tag) {
        this.tag = tag;
    }

    public boolean validate(
            final Class<? extends O> originalClass,
            final Class<? extends D> debugClass) {

        final List<Method> unimplementedMethods = new ArrayList<>();
        final Method[] wrappedClientMethods = originalClass.getMethods();
        Method candidate;

        for (final Method method : wrappedClientMethods) {
            if (!isIgnorable(method)) {
                candidate = getMethod(debugClass, method);
                if (candidate == null
                        && (getMethod(Object.class, method) == null)) {
                    unimplementedMethods.add(method);
                }
            }
        }

        if (unimplementedMethods.isEmpty()) {
            Log.i(tag, "All methods implemented :)");
        } else {
            Log.e(tag, "-----------------------------");
            for (final Method method : unimplementedMethods) {
                Log.e(tag, debugClass.getSimpleName() + " does not implement: " + method);
            }
            Log.e(tag, "-----------------------------");
        }

        return unimplementedMethods.isEmpty();
    }


    private static boolean isIgnorable(@NonNull final Method method) {
        return Modifier.isPrivate(method.getModifiers()) || Modifier.isStatic(method.getModifiers());
    }

    @Nullable
    private static Method getMethod(final Class<?> clazz, final Method method) {
        try {
            return clazz.getDeclaredMethod(method.getName(), method.getParameterTypes());
        } catch (NoSuchMethodException e) {
            return null;
        }
    }
}
