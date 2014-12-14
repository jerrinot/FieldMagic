package uk.co.rockstable.experiements.codegen.reflection.perf.direct;

import uk.co.rockstable.experiements.codegen.reflection.extractors.Extractor;
import uk.co.rockstable.experiements.codegen.reflection.extractors.ExtractorFactory;
import uk.co.rockstable.experiements.codegen.reflection.perf.domain.DomainObject;

public class DirectExtractorFactory extends ExtractorFactory {

    public Extractor create(Class<?> clazz, String field) {
        if (!DomainObject.class.equals(clazz)) {
            throw new IllegalArgumentException("Unknown class " + clazz);
        }
        if ("timestamp".equals(field)) {
            return new DirectTimestampExtractor();
        } else if ("position".equals(field)) {
            return new DirectPositionExtractor();
        } else if ("name".equals(field)) {
            return new DirectNameExtractor();
        } else {
            throw new IllegalArgumentException("Unknown field " + field);
        }
    }

    private static class DirectTimestampExtractor implements Extractor {
        @Override
        public Object extract(Object o) {
            return ((DomainObject)o).timestamp;
        }
    }

    private static class DirectPositionExtractor implements Extractor {
        @Override
        public Object extract(Object o) {
            return ((DomainObject)o).position;
        }
    }

    private static class DirectNameExtractor implements Extractor {
        @Override
        public Object extract(Object o) {
            return ((DomainObject)o).name;
        }
    }

}