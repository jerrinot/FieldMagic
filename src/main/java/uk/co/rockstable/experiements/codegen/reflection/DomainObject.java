package uk.co.rockstable.experiements.codegen.reflection;

public class DomainObject {
    public final Long timestamp;
    public final int position;
    public final String name;

    public DomainObject(long timestamp, int position, String name) {
        this.timestamp = timestamp;
        this.position = position;
        this.name = name;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }
}
