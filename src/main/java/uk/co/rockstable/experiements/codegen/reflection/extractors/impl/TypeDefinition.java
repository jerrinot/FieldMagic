package uk.co.rockstable.experiements.codegen.reflection.extractors.impl;

public enum TypeDefinition {
    OBJECT(false, null, null, null),
    INT(true, "I", "java/lang/Integer", Integer.TYPE),
    BOOLEAN(true, "Z", "java/lang/Boolean", Boolean.TYPE),
    CHAR(true, "C", "java/lang/Character", Character.TYPE),
    BYTE(true, "B", "java/lang/Byte", Byte.TYPE),
    SHORT(true, "S", "java/lang/Short", Short.TYPE),
    LONG(true, "J", "java/lang/Long", Long.TYPE),
    FLOAT(true, "F", "java/lang/Float", Float.TYPE),
    DOUBLE(true, "D", "java/lang/Double", Double.TYPE);

    private final boolean primitive;
    private final Class<?> primitiveClassName;
    private final String jvmPrimitiveTypeCode;
    private final String boxingClass;
    private final String boxingMethodSignature;

    private TypeDefinition(boolean primitive, String jvmPrimitiveTypeCode, String boxingClass, Class<?> primitiveClassName) {
        this.primitive = primitive;
        this.jvmPrimitiveTypeCode = jvmPrimitiveTypeCode;
        this.boxingClass = boxingClass;
        if (primitive) {
            boxingMethodSignature = "(" + jvmPrimitiveTypeCode + ")L" + boxingClass + ";";
        } else {
            boxingMethodSignature = null;
        }
        this.primitiveClassName = primitiveClassName;
    }

    public boolean isPrimitive() {
        return primitive;
    }

    public String getBoxingClass() {
        return boxingClass;
    }

    public String getBoxingMethodSignature() {
        return boxingMethodSignature;
    }

    public String getFieldTypeString(Class<?> clazz) {
        if (primitive) {
            return jvmPrimitiveTypeCode;
        } else {
            return "L" + clazz.getName().replace('.', '/') + ";";
        }
    }

    public static TypeDefinition fromClass(Class<?> clazz) {
        if (clazz.isPrimitive()) {
            for (TypeDefinition type : TypeDefinition.values()) {
                if (clazz.equals(type.primitiveClassName)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Unknown primitive class type: " + clazz);
        } else {
            return TypeDefinition.OBJECT;
        }
    }
}