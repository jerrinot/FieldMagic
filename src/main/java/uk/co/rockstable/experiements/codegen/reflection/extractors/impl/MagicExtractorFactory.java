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
            return createMagicExtractorInstance(field);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private Extractor createMagicExtractorInstance(Field field) {
        ClassWriter cw = new ClassWriter(0);
        MethodVisitor mv;
        final String generatedClassName = "uk/co/rockstable/experiements/codegen/reflection/GeneratedExtractor$" + ID.incrementAndGet();
        String className = field.getDeclaringClass().getName().replace('.', '/');
        String fieldName = field.getName();
        String fieldType = field.getType().getName().replace('.', '/');

        cw.visit(52, ACC_PUBLIC + ACC_SUPER, generatedClassName,
                null, "sun/reflect/MagicAccessorImpl", new String[]{"uk/co/rockstable/experiements/codegen/reflection/extractors/Extractor"});

        {
            mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "extract", "(Ljava/lang/Object;)Ljava/lang/Object;", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 1);
            mv.visitFieldInsn(GETFIELD, className, fieldName, "L" + fieldType + ";");
            mv.visitInsn(ARETURN);
            mv.visitMaxs(1, 2);
            mv.visitEnd();
        }
        cw.visitEnd();

        final byte[] impl = cw.toByteArray();

        ClassLoader cl = UnsafeUtils.MAGIC_CLASS_LOADER;
        Class clazz = UnsafeUtils.UNSAFE.defineClass(generatedClassName, impl, 0, impl.length, cl, null);
        try {
            return (Extractor) clazz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
