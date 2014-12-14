package uk.co.rockstable.experiements.codegen.reflection;

public class DirectMatcher implements Matcher<DomainObject> {
    @Override
    public boolean isMatching(DomainObject object) {
        long timestamp = object.timestamp;
        int position = object.position;
        String name = object.name;
        return Utils.isEven(timestamp) && position == 0 && "foo".equals(name);
    }
}
