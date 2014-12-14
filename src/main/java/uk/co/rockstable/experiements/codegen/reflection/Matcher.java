package uk.co.rockstable.experiements.codegen.reflection;

public interface Matcher<T> {
    boolean isMatching(T object);
}
