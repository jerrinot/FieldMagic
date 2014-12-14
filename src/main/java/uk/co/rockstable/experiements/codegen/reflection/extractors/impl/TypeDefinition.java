package uk.co.rockstable.experiements.codegen.reflection.extractors.impl;

public enum TypeDefinition {
    OBJECT(false, null, null, null),
    INT(true, "I", "java/lang/Integer", "int"),
    BOOLEAN(true, "Z", "java/lang/Boolean", "boolean"),
    CHAR(true, "C", "java/lang/Character", "char"),
    BYTE(true, "B", "java/lang/Byte", "byte"),
    SHORT(true, "S", "java/lang/Short", "short"),
    LONG(true, "J", "java/lang/Long", "long"),
    FLOAT(true, "F", "java/lang/Float", "float"),
    DOUBLE(true, "D", "java/lang/Double", "double");

    private final boolean primitive;
    private final String primitiveClassName;
    private final String jvmPrimitiveTypeCode;
    private final String boxingClass;
    private final String boxingMethodSignature;

    private TypeDefinition(boolean primitive, String jvmPrimitiveTypeCode, String boxingClass, String primitiveClassName) {
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
        String className = clazz.getName();
        if (clazz.isPrimitive()) {
            for (TypeDefinition type : TypeDefinition.values()) {
                if (className.equals(type.primitiveClassName)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Unknown primitive class type: " + clazz);
        } else {
            return TypeDefinition.OBJECT;
        }
    }
}