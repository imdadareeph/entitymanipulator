package com.app.entity;

/**
 * @author imdadareeph
 */
public class MainApp {
  public static void main(String[] args) {
   
	  showDetails();
   
  }
  
  
  
  public static void saveEmpDetails(){
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
  
  public static void showDetails(){
	  String stmnt = "select e from EmpDetails e";
	  JPAUtil.executeSelectQry(stmnt);
  }
 
  
  
}
