package uk.co.rockstable.experiements.codegen.reflection.extractors;

public interface Extractor<T> {
    <R> R extract(T o);
}
