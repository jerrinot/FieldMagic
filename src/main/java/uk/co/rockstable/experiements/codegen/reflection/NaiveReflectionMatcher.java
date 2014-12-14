package uk.co.rockstable.experiements.codegen.reflection;

import java.lang.reflect.Field;

public class NaiveReflectionMatcher implements Matcher<DomainObject> {

    @Override
    public boolean isMatching(DomainObject object) {
        try {
            Field timestampField = DomainObject.class.getDeclaredField("timestamp");
            timestampField.setAccessible(true);
            Field positionField = DomainObject.class.getDeclaredField("position");
            positionField.setAccessible(true);
            Field nameField = DomainObject.class.getDeclaredField("name");
            nameField.setAccessible(true);

            long timestamp = (long) timestampField.get(object);
            int position = (int) positionField.get(object);
            String name = (String) nameField.get(object);

            return Utils.isEven(timestamp) && position == 0 && "foo".equals(name);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
