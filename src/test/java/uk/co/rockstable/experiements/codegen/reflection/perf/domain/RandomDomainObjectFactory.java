package uk.co.rockstable.experiements.codegen.reflection.perf.domain;

import org.apache.commons.lang3.RandomUtils;

public class RandomDomainObjectFactory {

    public static DomainObject create() {
        String name = DICTIONARY[RandomUtils.nextInt(0, DICTIONARY.length)];
        long timestamp = System.currentTimeMillis();
        int position = RandomUtils.nextInt(0, 10);
        return new DomainObject(timestamp, position, name);
    }

    private static final String[] DICTIONARY = new String[]{"foo", "bar", "hazelcast", "rocks", "always"};
}
