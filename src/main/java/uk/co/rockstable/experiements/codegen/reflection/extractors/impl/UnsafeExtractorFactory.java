package uk.co.rockstable.experiements.codegen.reflection.extractors.impl;

import uk.co.rockstable.experiements.codegen.reflection.extractors.Extractor;
import uk.co.rockstable.experiements.codegen.reflection.extractors.ExtractorFactory;
import uk.co.rockstable.experiements.codegen.reflection.utils.UnsafeUtils;

import java.lang.reflect.Field;

public class UnsafeExtractorFactory extends ExtractorFactory {

    @Override
    public Extractor create(Class<?> clazz, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            return createExtractor(field);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private Extractor createExtractor(Field field) {
        Class<?> fieldType = field.getType();

        if (fieldType.isArray()) {
            throw new UnsupportedOperationException("Arrays are not supported yet");
        } else if (fieldType.isPrimitive()) {
            return createPrimitiveExtractor(field, fieldType);
        } else {
            return new ObjectExtractor(field);
        }
    }

    private Extractor createPrimitiveExtractor(Field field, Class<?> fieldType) {
        if (fieldType.equals(int.class)) {
            return new IntExtractor(field);
        } else if (fieldType.equals(boolean.class)) {
            return new BooleanExtractor(field);
        } else if (fieldType.equals(char.class)) {
            return new CharExtractor(field);
        } else if (fieldType.equals(byte.class)) {
            return new ByteExtractor(field);
        } else if (fieldType.equals(short.class)) {
            return new ShortExtractor(field);
        } else if (fieldType.equals(long.class)) {
            return new LongExtractor(field);
        } else if (fieldType.equals(float.class)) {
            return new FloatExtractor(field);
        } else if (fieldType.equals(double.class)) {
            return new DoubleExtractor(field);
        } else {
            throw new UnsupportedOperationException("Unsupported field " + fieldType);
        }
    }

    public static final class DoubleExtractor implements Extractor {
        private final long offset;

        public DoubleExtractor(Field field) {
            offset = UnsafeUtils.UNSAFE.objectFieldOffset(field);
        }

        @Override
        public <T> T extract(Object o) {
            return (T)(Object)UnsafeUtils.UNSAFE.getDouble(o, offset);
        }
    }

    public static final class FloatExtractor implements Extractor {
        private final long offset;

        public FloatExtractor(Field field) {
            offset = UnsafeUtils.UNSAFE.objectFieldOffset(field);
        }

        @Override
        public <T> T extract(Object o) {
            return (T)(Object)UnsafeUtils.UNSAFE.getFloat(o, offset);
        }
    }

    public static final class LongExtractor implements Extractor {
        private final long offset;

        public LongExtractor(Field field) {
            offset = UnsafeUtils.UNSAFE.objectFieldOffset(field);
        }

        @Override
        public <T> T extract(Object o) {
            return (T)(Object)UnsafeUtils.UNSAFE.getLong(o, offset);
        }
    }

    public static final class ShortExtractor implements Extractor {
        private final long offset;

        public ShortExtractor(Field field) {
            offset = UnsafeUtils.UNSAFE.objectFieldOffset(field);
        }

        @Override
        public <T> T extract(Object o) {
            return (T)(Object)UnsafeUtils.UNSAFE.getShort(o, offset);
        }
    }

    public static final class ByteExtractor implements Extractor {
        private final long offset;

        public ByteExtractor(Field field) {
            offset = UnsafeUtils.UNSAFE.objectFieldOffset(field);
        }

        @Override
        public <T> T extract(Object o) {
            return (T)(Object)UnsafeUtils.UNSAFE.getByte(o, offset);
        }
    }

    public static final class CharExtractor implements Extractor {
        private final long offset;

        public CharExtractor(Field field) {
            offset = UnsafeUtils.UNSAFE.objectFieldOffset(field);
        }

        @Override
        public <T> T extract(Object o) {
            return (T)(Object)UnsafeUtils.UNSAFE.getChar(o, offset);
        }
    }


    public static final class BooleanExtractor implements Extractor {
        private final long offset;

        public BooleanExtractor(Field field) {
            offset = UnsafeUtils.UNSAFE.objectFieldOffset(field);
        }

        @Override
        public <T> T extract(Object o) {
            return (T)(Object)UnsafeUtils.UNSAFE.getBoolean(o, offset);
        }
    }

    public static final class IntExtractor implements Extractor {
        private final long offset;

        public IntExtractor(Field field) {
            offset = UnsafeUtils.UNSAFE.objectFieldOffset(field);
        }

        @Override
        public <T> T extract(Object o) {
            return (T)(Object)UnsafeUtils.UNSAFE.getInt(o, offset);
        }
    }

    private static final class ObjectExtractor implements Extractor {
        private final long offset;

        private ObjectExtractor(Field field) {
            offset = UnsafeUtils.UNSAFE.objectFieldOffset(field);
        }

        @Override
        public <T> T extract(Object o) {
            return (T) UnsafeUtils.UNSAFE.getObject(o, offset);
        }
    }
}
