package uk.co.rockstable.experiements.codegen.reflection.extractors.impl;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import uk.co.rockstable.experiements.codegen.reflection.extractors.Extractor;
import uk.co.rockstable.experiements.codegen.reflection.extractors.ExtractorFactory;
import uk.co.rockstable.experiements.codegen.reflection.utils.UnsafeUtils;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

import static org.objectweb.asm.Opcodes.*;

public class MagicExtractorFactory extends ExtractorFactory {
    private static AtomicInteger ID = new AtomicInteger();


    public Extractor create(Class<?> clazz, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            return createMagicExtractorInstance(clazz, field);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private Extractor createMagicExtractorInstance(Class<?> clazz, Field field) {
        final String generatedClassName = generateClassName();
        final byte[] generatedBytecode = generateExtractorByteCode(field, generatedClassName);
        ClassLoader cl = clazz.getClassLoader();
        Class<?> generatedClass = UnsafeUtils.UNSAFE.defineClass(generatedClassName, generatedBytecode, 0, generatedBytecode.length, cl, null);
        return createNewInstance(generatedClass);
    }

    private Extractor createNewInstance(Class<?> generatedClass) {
        try {
            return (Extractor) generatedClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] generateExtractorByteCode(Field field, String generatedClassName) {
        String declaringClassName = field.getDeclaringClass().getName().replace('.', '/');
        ClassWriter cw = new ClassWriter(0);
        MethodVisitor mv;

        String fieldName = field.getName();
        Class<?> fieldTypeClass = field.getType();
        TypeDefinition fieldTypeEnum = TypeDefinition.fromClass(fieldTypeClass);
        String fieldTypeString = fieldTypeEnum.getFieldTypeString(fieldTypeClass);

        cw.visit(52, ACC_PUBLIC + ACC_SUPER, generatedClassName,
                null, "sun/reflect/MagicAccessorImpl",
                new String[]{"uk/co/rockstable/experiements/codegen/reflection/extractors/Extractor"});

        writeConstructor(cw);

        {
            mv = cw.visitMethod(ACC_PUBLIC, "extract", "(Ljava/lang/Object;)Ljava/lang/Object;", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 1);
            mv.visitFieldInsn(GETFIELD, declaringClassName, fieldName, fieldTypeString);
            boxPrimitiveTypeIfNecessary(mv, fieldTypeEnum);
            mv.visitInsn(ARETURN);
            mv.visitMaxs(1, 2);
            mv.visitEnd();
        }
        cw.visitEnd();
        return cw.toByteArray();
    }

    private void writeConstructor(ClassWriter cw) {
        MethodVisitor mv;
        mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();
    }

    private String generateClassName() {
        return "uk/co/rockstable/experiements/codegen/reflection/GeneratedExtractor$" + ID.incrementAndGet();
    }

    private void boxPrimitiveTypeIfNecessary(MethodVisitor mv, TypeDefinition fieldTypeEnum) {
        if (fieldTypeEnum.isPrimitive()) {
            String boxingClass = fieldTypeEnum.getBoxingClassCode();
            String boxingMethodSignature = fieldTypeEnum.getBoxingMethodSignature();
            mv.visitMethodInsn(INVOKESTATIC, boxingClass, "valueOf", boxingMethodSignature, false);
        }
    }
}
