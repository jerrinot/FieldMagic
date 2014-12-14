package uk.co.rockstable.experiements.codegen.reflection.func.domain;

public class DomainObject {
    private final String name;
    private final int id;

    private boolean _boolean = true;
    private char _char = Character.MAX_VALUE;
    private byte _byte = Byte.MAX_VALUE;
    private short _short = Short.MAX_VALUE;
    private int _int = Integer.MAX_VALUE;
    private long _long = Long.MAX_VALUE;
    private float _float = Float.MAX_VALUE;
    private double _double = Double.MAX_VALUE;

    public int[] primitiveIntArray = new int[0];
    public Integer[] integerArray = new Integer[0];

    public DomainObject(String name, int id) {
        this.name = name;
        this.id = id;
    }
}
