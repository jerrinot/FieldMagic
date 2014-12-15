package uk.co.rockstable.experiements.codegen.reflection.extractors;

import uk.co.rockstable.experiements.codegen.reflection.extractors.impl.CachingFactoryDecorator;
import uk.co.rockstable.experiements.codegen.reflection.extractors.impl.MagicExtractorFactory;
import uk.co.rockstable.experiements.codegen.reflection.extractors.impl.ReflectionExtractorFactory;
import uk.co.rockstable.experiements.codegen.reflection.extractors.impl.UnsafeExtractorFactory;

public abstract class ExtractorFactory {
    public abstract <T> Extractor<T> create(Class<T> clazz, String field);

    public static ExtractorFactory newInstance(Type type) {
        return newInstance(type, true);
    }

    public static ExtractorFactory newInstance(Type type, boolean caching) {
        ExtractorFactory factory;
        switch (type) {
            case MAGIC:
                factory = new MagicExtractorFactory();
                break;
            case REFLECTION:
                factory = new ReflectionExtractorFactory();
                break;
            case UNSAFE:
                factory = new UnsafeExtractorFactory();
                break;
            default:
                throw new IllegalArgumentException("Unknown ExtractFactory type " + type);
        }
        return caching ? new CachingFactoryDecorator(factory) : factory;
    }


    public static enum Type {
        MAGIC,
        REFLECTION,
        UNSAFE
    }
}
