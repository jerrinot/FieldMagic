package uk.co.rockstable.experiements.codegen.reflection.perf.reflectasm;

import com.esotericsoftware.reflectasm.FieldAccess;
import uk.co.rockstable.experiements.codegen.reflection.extractors.Extractor;
import uk.co.rockstable.experiements.codegen.reflection.extractors.ExtractorFactory;

import java.lang.reflect.Field;

public class ReflectasmExtractorFactory extends ExtractorFactory {

    @Override
    public <T> Extractor<T> create(Class<T> clazz, String fieldName) {
        return new ReflectasmObjectExtractor<T>(clazz, fieldName);
    }

    private static class ReflectasmObjectExtractor<T> implements Extractor<T> {
        private final int fieldIndex;
        private final FieldAccess fieldAccess;

        public ReflectasmObjectExtractor(Class<T> clazz, String field) {
            fieldAccess = FieldAccess.get(clazz);
            fieldIndex = fieldAccess.getIndex(field);
        }

        @Override
        public <R> R extract(T o) {
            return (R) fieldAccess.get(o, fieldIndex);
        }
    }

}
