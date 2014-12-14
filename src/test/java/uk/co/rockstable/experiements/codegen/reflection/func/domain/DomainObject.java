package uk.co.rockstable.experiements.codegen.reflection.func.domain;

public class DomainObject {
    private final String name;
    private final int id;

    public boolean _boolean = true;
    public char _char = Character.MAX_VALUE;
    public byte _byte = Byte.MAX_VALUE;
    public short _short = Short.MAX_VALUE;
    public int _int = Integer.MAX_VALUE;
    public long _long = Long.MAX_VALUE;
    public float _float = Float.MAX_VALUE;
    public double _double = Double.MAX_VALUE;

    public int[] primitiveIntArray = new int[0];
    public Integer[] integerArray = new Integer[0];

    public DomainObject(String name, int id) {
        this.name = name;
        this.id = id;
    }
}
