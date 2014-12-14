package uk.co.rockstable.experiements.codegen.reflection.extractors.impl;

import uk.co.rockstable.experiements.codegen.reflection.extractors.Extractor;
import uk.co.rockstable.experiements.codegen.reflection.extractors.ExtractorFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CachingFactoryDecorator extends ExtractorFactory {
    private final ExtractorFactory delegate;
    private final ConcurrentHashMap<Class<?>, ConcurrentHashMap<String, Extractor>> cache;

    public CachingFactoryDecorator(ExtractorFactory delegate) {
        this.delegate = delegate;
        this.cache = new ConcurrentHashMap<>();
    }

    @Override
    public Extractor create(Class<?> clazz, String field) {
        ConcurrentMap<String, Extractor> classCache = cache.get(clazz);
        if (classCache == null) {
            classCache = cache.computeIfAbsent(clazz, (ignored) -> new ConcurrentHashMap<>() );
        }
        Extractor extractor = classCache.get(field);
        if (extractor == null) {
            extractor = classCache.computeIfAbsent(field, (ignored) -> delegate.create(clazz, field));
        }
        return extractor;
    }
}
