package uk.co.rockstable.experiements.codegen.reflection.extractors;

public interface Extractor {
    <T> T extract(Object o);
}
