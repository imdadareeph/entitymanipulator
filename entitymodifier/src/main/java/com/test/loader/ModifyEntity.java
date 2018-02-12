/**
 * 
 */
package com.test.loader;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.NotFoundException; 
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
	
	public <T> void addMethod(){
		try {
			Class cc = getClazzFromFilePath();
			//String meth = "public int hashCode() {final int PRIME = 59; int result = 1; result = (result*PRIME) + (this.getFullName() == null ? 43 : this.getName().hashCode()); result = (result*PRIME) + (this.getValue() == null ? 43 : this.getValue().hashCode()); return result;}";
           String meth ="public int hashCode() { final int prime = 31; int result = 1; result = prime * result + ((fullName == null) ? 0 : fullName.hashCode()); return result; }";
		   String eqlMethod ="public boolean equals(Object obj) { if (this == obj) return true; if (obj == null) return false; if (getClass() != obj.getClass()) return false; EmpDetails other = (EmpDetails) obj; if (fullName == null) { if (other.fullName != null) return false; } else if (!fullName.equals(other.fullName)) return false; return true;}";	
           CtClass ctclass = ClassPool.getDefault().get(cc.getName());
            CtMethod newmethod = CtNewMethod.make(meth,ctclass);
            //CtMethod newmethod2 = CtNewMethod.make(eqlMethod,ctclass);
            ctclass.addMethod(newmethod);
           // ctclass.addMethod(newmethod2);
            ctclass.writeFile();
            ClassPool pool = ClassPool.getDefault();
		       
	        // first we need to load the class via javassist
	        CtClass origClass = pool.get(cc.getName());
            CtClass subClass = pool.makeClass(cc.getName() + "_intcpted", origClass);
		       
	        overrideInterceptedMethods(subClass);
	       
	        // now let's get the real class from it
	        @SuppressWarnings("unchecked")
	        Class<T> interceptedClass = subClass.toClass();

            for(Method me: interceptedClass.getDeclaredMethods()){ //test print, ok
                System.out.println(me);
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
	
	 private <T> Class<T> createSubClass(Class<T> cls)
			    throws NotFoundException, CannotCompileException
			    {
			        ClassPool pool = ClassPool.getDefault();
			       
			        // first we need to load the class via javassist
			        CtClass origClass = pool.get(cls.getName());
			       
			        // we create a new subclass with a certain postfix
			        CtClass subClass = pool.makeClass(cls.getName() + "_intcpted", origClass);
			       
			        overrideInterceptedMethods(subClass);
			       
			        // now let's get the real class from it
			        @SuppressWarnings("unchecked")
			        Class<T> interceptedClass = subClass.toClass();
			       
			        return interceptedClass;
			    }
	 private void overrideInterceptedMethods(CtClass subClass)
			    throws CannotCompileException
			    {
			        CtMethod[] allMethods = subClass.getMethods();
			       
			        for (CtMethod method : allMethods)
			        {
			            overrideInterceptedMethod(subClass, method);
			        }
			    }


			    private void overrideInterceptedMethod(CtClass subClass, CtMethod method)
			    throws CannotCompileException {
			        if (!method.visibleFrom(subClass) ||
			            ((method.getModifiers() & (Modifier.FINAL | Modifier.STATIC)) > 0))
			        {
			            // we cannot delegate non visible, final or static methods
			            return;
			        }
			       
			        String methodName = method.getLongName();
			        if (methodName.startsWith("java.lang.Object."))
			        {
			            // we also have to skip methods we derive from 'java.lang.Object'
			            return;
			        }
			       
			        CtMethod overridenMethod = CtNewMethod.delegator(method, subClass);
			        subClass.addMethod(overridenMethod);
			       
			        overridenMethod.insertBefore("{System.out.println(\"juuubel!:\");};");
			    }
	
}
