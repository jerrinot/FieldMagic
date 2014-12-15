package uk.co.rockstable.experiements.codegen.reflection.extractors.impl;

import uk.co.rockstable.experiements.codegen.reflection.extractors.Extractor;
import uk.co.rockstable.experiements.codegen.reflection.extractors.ExtractorFactory;

import java.lang.reflect.Field;

public class ReflectionExtractorFactory extends ExtractorFactory {

    @Override
    public <T> Extractor<T> create(Class<T> clazz, String field) {
        return new ReflectionExtractor(clazz, field);
    }

    private static class ReflectionExtractor implements Extractor {
        private final Field field;

        public ReflectionExtractor(Class<?> clazz, String fieldName) {
            try {
                field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        }


        @Override
        public Object extract(Object o) {
            try {
                return field.get(o);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
