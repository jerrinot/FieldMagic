package uk.co.rockstable.experiements.codegen.reflection.utils;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeUtils {
    public static final ClassLoader MAGIC_CLASS_LOADER = findMagicClassLoader();
    public static final Unsafe UNSAFE;

    static {
        try {
            @SuppressWarnings("ALL")
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            UNSAFE = (Unsafe) theUnsafe.get(null);
        } catch (Exception e) {
            throw new IllegalStateException("Error while getting Unsafe", e);
        }
    }

    private static ClassLoader findMagicClassLoader() {
        try {
            Class<?> clazz = Class.forName("sun.reflect.ConstructorAccessor");
            ClassLoader cl = clazz.getClassLoader();
            if (cl == null) {
                cl = ClassLoader.getSystemClassLoader();
            }
            return cl;
        } catch (Throwable ignore) {
        }
        return null;
    }
}
