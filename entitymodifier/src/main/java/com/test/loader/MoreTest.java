/**
 * 
 */
package com.test.loader;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtNewConstructor;
import javassist.Modifier;
import javassist.bytecode.AccessFlag;
import javassist.bytecode.Bytecode;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.DuplicateMemberException;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.Opcode;
import javassist.util.proxy.FactoryHelper;

/**
 * @author imdadareeph
 *
 */
public class MoreTest {

	public static void main(String[] args)  {
		MoreTest mt = new MoreTest();
		//me.callModifyClass();
		mt.callModifyClass();
    }

	
	public void callModifyClass(){
		try {
			modifyClass();
		} catch (MalformedURLException | ClassNotFoundException | NoSuchMethodException | SecurityException
				| InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public void modifyClass() throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		URLClassLoader child = new URLClassLoader (new URL[] {new URL("file://Users/imdadareeph/Documents/dev/eclipse/workspaces/multi/entitymanupulator/entitylib/target/entitylib-0.0.1-SNAPSHOT.jar")}, ModifyEntity.class.getClassLoader());
		//URLClassLoader child = new URLClassLoader(myJar.toURL(), this.getClass().getClassLoader());
		Class classToLoad = Class.forName("com.app.entity.EmpDetails", true, child);
		int hash = classToLoad.hashCode();
		Method method = classToLoad.getDeclaredMethod("getEmpNumber");
		Object instance = classToLoad.newInstance();
		Object result = method.invoke(instance);
	}
	
	private ClassFile classFile; 
	 
    private CtClass ctClass; 
 
    private File extraClassPath; 
 
    public void setClassPath(File file) { 
        this.extraClassPath = file; 
    } 
 
    /** 
     *  
     * @param iface The interface the new class should implement 
     * @param oldlassName The class to be extended 
     * @param newClassName the name of the new class to be created 
     */ 
    public MoreTest(Class<?> iface, String oldlassName, String newClassName) { 
 
        try { 
            ClassPool pool = ClassPool.getDefault(); 
            ctClass = pool.makeClass(newClassName); 
            classFile = ctClass.getClassFile(); 
            classFile.setSuperclass(oldlassName); 
 
            classFile.setName(newClassName); 
 
            classFile.setInterfaces(new String[] { iface.getName() }); 
 
        } catch (Exception e) { 
            throw new RuntimeException(e); 
        } 
    } 
    
    public MoreTest() {
		// TODO Auto-generated constructor stub
	}
    
    public void callAddDoubleField(){
    	URLClassLoader child;
    	Class classToLoad = null;
		try {
			child = new URLClassLoader (new URL[] {new URL("file://Users/imdadareeph/Documents/dev/eclipse/workspaces/multi/entitymanupulator/entitylib/target/entitylib-0.0.1-SNAPSHOT.jar")}, ModifyEntity.class.getClassLoader());
		    classToLoad = Class.forName("com.app.entity.EmpDetails", true, child);
		} catch (MalformedURLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(null!=classToLoad){
			addDoubleField(classToLoad.getSimpleName());
		}
    	
    }
    
    public Class getClazzFromFilePath(){
    	URLClassLoader child;
    	Class classToLoad = null;
		try {
			child = new URLClassLoader (new URL[] {new URL("file://Users/imdadareeph/Documents/dev/eclipse/workspaces/multi/entitymanupulator/entitylib/target/entitylib-0.0.1-SNAPSHOT.jar")}, ModifyEntity.class.getClassLoader());
		    classToLoad = Class.forName("com.app.entity.EmpDetails", true, child);
		} catch (MalformedURLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return classToLoad;
    }



	/** Adds a new field of type double to the customized class */
	public void addDoubleField(String fieldName) {
	    // FIXME: this should support default values but does not

	    ClassFile classFile = ctClass.getClassFile();
	    ConstPool constPool = classFile.getConstPool();
	    try {
	        // add field
	        FieldInfo fieldInfo = new FieldInfo(constPool, fieldName, "D");
	        classFile.addField(fieldInfo);

	        CtConstructor ctor = CtNewConstructor.defaultConstructor(ctClass);
	        ctClass.addConstructor(ctor);

	        addDoubleSetter(classFile, fieldName);
	        addDoubleGetter(classFile, fieldName);

	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
	/** Writes the class file to the classpath and returns a class object */ 
    public Class<?> saveClass() { 
        ClassFile classFile = ctClass.getClassFile(); 
 
        try { 
            if (!extraClassPath.exists()) { 
                extraClassPath.mkdirs(); 
            } 
            FactoryHelper.writeFile(classFile, extraClassPath.getPath()); 
            ClassLoader loader = getClass().getClassLoader(); 
 
            Class<?> cls = FactoryHelper.toClass(classFile, loader); 
            return cls; 
            // load class 
        } catch (Exception e) { 
            throw new RuntimeException(e); 
        } 
    } 
 
    /** Creates a clone of original but with the class NewClass (which extends original's class) */ 
    public static <T> T reclass(T original, Class<? extends T> newClass) { 
        Class<?> origClass = original.getClass(); 
 
        T newObj; 
        try { 
            Constructor<? extends T> ctor = newClass.getConstructor(); 
            newObj = ctor.newInstance(); 
 
            while (origClass != null) { 
                Field[] fields = origClass.getDeclaredFields(); 
                for (Field field : fields) { 
                    field.setAccessible(true); 
                    int modifiers = field.getModifiers(); 
                    if (Modifier.isStatic(modifiers)) { 
                        continue; 
                    } 
                    Object value = field.get(original); 
                    field.set(newObj, value); 
                } 
                origClass = origClass.getSuperclass(); 
            } 
        } catch (Exception e) { 
            throw new RuntimeException(e); 
        } 
        return newObj; 
    } 
 
    /**
     * capitalize the first letter of the string 
     *  
     * @param str 
     * @return 
     */ 
    private String ucfirst(String str) { 
        return str.substring(0, 1).toUpperCase() + str.substring(1); 
    } 
 
    /**
     * Add a simple getter with signature "double getFoo()" to the class, which simply returns the value of the  
     * field fieldName  
     * @param ctClass 
     * @param classFile 
     * @param fieldName 
     * @throws DuplicateMemberException 
     */ 
    private void addDoubleGetter(ClassFile classFile, String fieldName) 
            throws DuplicateMemberException { 
        ConstPool constPool = classFile.getConstPool(); 
 
        // double getFoo() 
        MethodInfo getter = new MethodInfo(constPool, "get" + ucfirst(fieldName), "()D"); 
 
        Bytecode code = new Bytecode(constPool, 2, 1); 
 
        // load this 
        code.addAload(0); 
 
        code.addGetfield(ctClass, fieldName, "D"); 
 
        // return with value 
        code.addOpcode(Opcode.DRETURN); 
        getter.setCodeAttribute(code.toCodeAttribute()); 
 
        getter.setAccessFlags(AccessFlag.PUBLIC); 
        classFile.addMethod(getter); 
    } 
 
    private void addDoubleSetter(ClassFile classFile, String fieldName) 
            throws DuplicateMemberException { 
        ConstPool constPool = classFile.getConstPool(); 
 
        // void setFoo(double) 
        MethodInfo setter = new MethodInfo(constPool, "set" + ucfirst(fieldName), "(D)V"); 
        Bytecode code = new Bytecode(constPool, 3, 3); 
 
        // load this 
        code.addAload(0); 
 
        // load param 
        code.addDload(1); 
        code.addPutfield(ctClass, fieldName, "D"); 
 
        code.addOpcode(Opcode.RETURN); 
 
        setter.setCodeAttribute(code.toCodeAttribute()); 
        setter.setAccessFlags(AccessFlag.PUBLIC); 
        classFile.addMethod(setter); 
 
    } 
}
