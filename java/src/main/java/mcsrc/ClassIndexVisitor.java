package mcsrc;

import org.objectweb.asm.*;

public class ClassIndexVisitor extends ClassVisitor {
	private String name;

	public ClassIndexVisitor(int api) {
		super(api);
    }

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		this.name = name;
		Indexer.addClassData(name, superName, interfaces, access);
	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		Entry.Field field = new Entry.Field(this.name, name, desc);
		Indexer.addMemberData(field.owner(), field);
		return super.visitField(access, name, desc, signature, value);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		Entry.Method methodEntry = new Entry.Method(this.name, name, desc);
		Indexer.addMemberData(methodEntry.owner(), methodEntry);
		return super.visitMethod(access, name, desc, signature, exceptions);
	}

}
