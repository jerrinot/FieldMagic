package uk.co.rockstable.experiements.codegen;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import sun.misc.Unsafe;

import java.io.FileDescriptor;
import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;

public interface MyDispatcher {

    int write(FileDescriptor fd, long address, int len) throws IOException;
    int read(FileDescriptor fd, long address, int len) throws IOException;

    static final MyDispatcher INSTANCE = Inner.getDispatcher();

    static class Inner implements Opcodes {
        private static MyDispatcher getDispatcher() {
            final String dispatcherImplClassName = "uk/co/rockstable/experiements/codegen/DispatcherAccessor";
            ClassWriter cw = new ClassWriter(0);
            FieldVisitor fv;
            MethodVisitor mv;

            cw.visit(V1_6, ACC_PUBLIC + ACC_SUPER, dispatcherImplClassName, null, "sun/reflect/MagicAccessorImpl", new String[] { "uk/co/rockstable/experiements/codegen/MyDispatcher" });

            {
                fv = cw.visitField(ACC_PRIVATE + ACC_FINAL + ACC_STATIC, "dispatcher", "Lsun/nio/ch/NativeDispatcher;", null, null);
                fv.visitEnd();
            }
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
                mv = cw.visitMethod(ACC_PUBLIC, "write", "(Ljava/io/FileDescriptor;JI)I", null, new String[] { "java/io/IOException" });
                mv.visitCode();
                mv.visitFieldInsn(GETSTATIC, dispatcherImplClassName, "dispatcher", "Lsun/nio/ch/NativeDispatcher;");
                mv.visitVarInsn(ALOAD, 1);
                mv.visitVarInsn(LLOAD, 2);
                mv.visitVarInsn(ILOAD, 4);
                mv.visitMethodInsn(INVOKEVIRTUAL, "sun/nio/ch/NativeDispatcher", "write", "(Ljava/io/FileDescriptor;JI)I", true);
                mv.visitInsn(IRETURN);
                mv.visitMaxs(5, 5);
                mv.visitEnd();
            }
            {
                mv = cw.visitMethod(ACC_PUBLIC, "read", "(Ljava/io/FileDescriptor;JI)I", null, new String[] { "java/io/IOException" });
                mv.visitCode();
                mv.visitFieldInsn(GETSTATIC, dispatcherImplClassName, "dispatcher", "Lsun/nio/ch/NativeDispatcher;");
                mv.visitVarInsn(ALOAD, 1);
                mv.visitVarInsn(LLOAD, 2);
                mv.visitVarInsn(ILOAD, 4);
                mv.visitMethodInsn(INVOKEVIRTUAL, "sun/nio/ch/NativeDispatcher", "read", "(Ljava/io/FileDescriptor;JI)I", true);
                mv.visitInsn(IRETURN);
                mv.visitMaxs(5, 5);
                mv.visitEnd();
            }
            {
                mv = cw.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
                mv.visitCode();
                mv.visitFieldInsn(GETSTATIC, "sun/nio/ch/SocketChannelImpl", "nd", "Lsun/nio/ch/NativeDispatcher;");
                mv.visitFieldInsn(PUTSTATIC, dispatcherImplClassName, "dispatcher", "Lsun/nio/ch/NativeDispatcher;");
                mv.visitInsn(RETURN);
                mv.visitMaxs(1, 0);
                mv.visitEnd();
            }
            cw.visitEnd();


            final byte[] impl = cw.toByteArray();

            final Unsafe unsafe = UnsafeUtils.UNSAFE;
            Class clazz = AccessController.doPrivileged(new PrivilegedAction<Class>() {
                @Override
                public Class run() {
                    ClassLoader cl = UnsafeUtils.MAGIC_CLASS_LOADER;
                    return unsafe.defineClass(dispatcherImplClassName, impl, 0, impl.length, cl, null);
                }
            });
            try {
                return (MyDispatcher) clazz.newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
