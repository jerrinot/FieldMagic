package uk.co.rockstable.experiements.codegen.reflection.perf.domain;

public class DomainObject {
    public final Long timestamp;
    public final Integer position;
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
