package com.test.loader;

import java.util.ArrayList;
import java.util.List;

import com.app.entity.EmpDetails;
import com.app.entity.JPAUtil;

/**
 * @author imdadareeph
 */

public class TestEntity {
	
	List<EmpDetails> emplist = new ArrayList<EmpDetails>();
	 EmpDetails empDet = new EmpDetails();
	 String stmnt = "select e from EmpDetails e";
	 String objType ="com.app.entity.EmpDetails";

	public static void main(String[] args) {
		TestEntity te = new TestEntity();
		te.showAllEmp();
		te.showAllEmpList();
	  }
	
	public void showAllEmp(){
		List<Object> res = JPAUtil.executeSelectQry(stmnt);
		 System.out.println("OBJECTS fetched :::: "+res);
		 for(Object str:res){
		    	System.out.println("OBJECTS :::: "+str);
		    	EmpDetails em = new EmpDetails();
		    	em =(EmpDetails) str;
		    	emplist.add(em);
		    }
	}
	
	public void showAllEmpList(){
		System.out.println("OBJECTS list :::: "+emplist);
	}
	
	public  void saveEmpDetails(){
		  String objType ="com.app.entity.EmpDetails";
		  EmpDetails emp = new EmpDetails();
		  emp.setEmailAddress("emp3@gmail.com");
		  emp.setEmpNumber((long) 13);
		  emp.setFullName("Emp Three");
		  try {
			  JPAUtil.saveEntity(emp,objType);
			} catch (Exception e) {
				System.out.println("exception while saving data");
			}
	  }
	
	
	
	public EmpDetails fetchEmpDetails(){		 
		 emplist = new ArrayList<EmpDetails>();
		 List<Object> res = JPAUtil.executeSelectQry(stmnt);
		 //System.out.println("All Emp :::: "+res);
		 for(Object str:res){
		    	System.out.println("All Emp :::: "+str);
		    	EmpDetails em = new EmpDetails();
		    	em =(EmpDetails) str;
		    	emplist.add(em);
		    	if(em.getFullName().equalsIgnoreCase("Emp One")){
		    		empDet = em;
		    	}
		    }
		 return empDet;
	}
	
	public  void updateEmpDetails(){
		 fetchEmpDetails();
		 empDet.setEmailAddress("emp1@gmail.com");
		 JPAUtil.updateEntity(empDet, objType);
		 List<Object> res2 = JPAUtil.executeSelectQry(stmnt);
		 System.out.println("OBJECTS after update :::: "+res2);
	}
}
