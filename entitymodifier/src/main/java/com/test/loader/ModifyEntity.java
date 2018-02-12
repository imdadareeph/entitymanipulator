/**
 * 
 */
package com.test.loader;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import com.app.entity.EmpDetails;

import java.lang.reflect.Constructor; 
import java.lang.reflect.Field; 
 
import javassist.ClassPool; 
import javassist.CtClass; 
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
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
public class ModifyEntity {

	public static void main(String[] args)  {
		ModifyEntity me = new ModifyEntity();
		//me.callModifyClass();
		me.addMethod();
    }
	
	public void addMethod(){
		try {
			Class cc = getClazzFromFilePath();
			//String meth = "public int hashCode() {final int PRIME = 59; int result = 1; result = (result*PRIME) + (this.getFullName() == null ? 43 : this.getName().hashCode()); result = (result*PRIME) + (this.getValue() == null ? 43 : this.getValue().hashCode()); return result;}";
           String meth ="public int hashCode() { final int prime = 31; int result = 1; result = prime * result + ((fullName == null) ? 0 : fullName.hashCode()); return result; }";
		   String hashMethod ="public boolean equals(Object obj) { if (this == obj) return true; if (obj == null) return false; if (getClass() != obj.getClass()) return false; EmpDetails other = (EmpDetails) obj; if (fullName == null) { if (other.fullName != null) return false; } else if (!fullName.equals(other.fullName)) return false; return true;}";	
           CtClass ctclass = ClassPool.getDefault().get(cc.getCanonicalName());
            CtMethod newmethod = CtNewMethod.make(meth,ctclass);
            ctclass.addMethod(newmethod);
            ctclass.writeFile();

            for(Method me: ctclass.toClass().getDeclaredMethods()){ //test print, ok
                System.out.println(me.getName());
            }

        } catch (Exception e) {
            e.printStackTrace();
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
	
	
}
