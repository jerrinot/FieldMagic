package uk.co.rockstable.experiements.codegen.reflection;

import java.lang.reflect.Field;

public class CachingReflectionMatcher implements Matcher<DomainObject> {
    private final Field timestampField;
    private final Field positionField;
    private final Field nameField;


    public CachingReflectionMatcher() {
        try {
            timestampField = DomainObject.class.getDeclaredField("timestamp");
            timestampField.setAccessible(true);
            positionField = DomainObject.class.getDeclaredField("position");
            positionField.setAccessible(true);
            nameField = DomainObject.class.getDeclaredField("name");
            nameField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean isMatching(DomainObject object) {
        try {
            long timestamp = (long) timestampField.get(object);
            int position = (int) positionField.get(object);
            String name = (String) nameField.get(object);
            return Utils.isEven(timestamp) && position == 0 && "foo".equals(name);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
