package uk.co.rockstable.experiements.codegen.reflection.extractors.impl;

public enum TypeDefinition {
    //this represent any non-primitive value
    OBJECT(null, null, null),

    INT("I", "java/lang/Integer", Integer.TYPE),
    BOOLEAN("Z", "java/lang/Boolean", Boolean.TYPE),
    CHAR("C", "java/lang/Character", Character.TYPE),
    BYTE("B", "java/lang/Byte", Byte.TYPE),
    SHORT("S", "java/lang/Short", Short.TYPE),
    LONG("J", "java/lang/Long", Long.TYPE),
    FLOAT("F", "java/lang/Float", Float.TYPE),
    DOUBLE("D", "java/lang/Double", Double.TYPE);

    private final boolean primitive;
    private final Class<?> primitiveClassName;
    private final String jvmPrimitiveTypeCode;
    private final String boxingClassCode;
    private final String boxingMethodSignature;

    private TypeDefinition(String jvmPrimitiveTypeCode, String boxingClassCode, Class<?> primitiveClass) {
        this.primitive = primitiveClass != null;
        this.jvmPrimitiveTypeCode = jvmPrimitiveTypeCode;
        this.boxingClassCode = boxingClassCode;
        if (primitive) {
            boxingMethodSignature = "(" + jvmPrimitiveTypeCode + ")L" + boxingClassCode + ";";
        } else {
            boxingMethodSignature = null;
        }
        this.primitiveClassName = primitiveClass;
    }

    public boolean isPrimitive() {
        return primitive;
    }

    public String getBoxingClassCode() {
        return boxingClassCode;
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
        } else if (clazz.isArray()) {
            throw new UnsupportedOperationException("Arrays are not supported yet.");
        } else {
            return TypeDefinition.OBJECT;
        }
    }
}