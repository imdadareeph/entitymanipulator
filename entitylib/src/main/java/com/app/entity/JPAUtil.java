package com.app.entity;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * @author imdadareeph
 */
public class JPAUtil {
  private static final String PERSISTENCE_UNIT_NAME = "PERSISTENCE";
  private static EntityManagerFactory factory;
  static EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

  public static EntityManagerFactory getEntityManagerFactory() {
    if (factory == null) {
      factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }
    return factory;
  }

  public static void shutdown() {
    if (factory != null) {
      factory.close();
    }
  }
  
  public static void saveEntity(Object obj,String objClass){
	  //EntityManager entityManager = null;
      try {
         entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
         entityManager.getTransaction().begin();
         // Save entity
         Object newObj = Class.forName(objClass).cast(obj);
         entityManager.persist(newObj);

         entityManager.getTransaction().commit();
         System.out.println("obj saved successfully");
      } catch (Exception e) {
         e.printStackTrace();
         if (entityManager != null) {
            System.out.println("Transaction is being rolled back.");
            entityManager.getTransaction().rollback();
         }
      } finally {
         if (entityManager != null) {
           // entityManager.close();
        	// JPAUtil.shutdown();
         }
      }
      
   }
  
  public static List<Object> executeSelectQry(String qry){
	  //EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
	    entityManager.getTransaction().begin();
	    // Check database version
	    String sql = qry;
	   // Class resultClass =EmpDetails.class;
	   // List<String> result2 = (List<String>) entityManager.createNamedQuery(qry, resultClass);

	    Query result = entityManager.createQuery(qry);
	    List<Object> result2 = result.getResultList();
	    /*for(Object str:result2){
	    	System.out.println(str.toString());
	    }*/
	    

	    entityManager.getTransaction().commit();
	   // entityManager.close();

	    //JPAUtil.shutdown();
	    return result2;
  }
  
  public static void updateEntity(Object obj,String objClass) {

      //EntityManager entityManager = null;
      try {
    	  //entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
         entityManager.getTransaction().begin();

         //Object object = entityManager.find(Class.forName(objClass).getClass(), 1l);
         Object newObj = Class.forName(objClass).cast(obj);

         entityManager.merge(newObj);

         entityManager.getTransaction().commit();
         System.out.println("object updated successfully");
         
      } catch (Exception e) {
         e.printStackTrace();
         if (entityManager != null) {
            System.out.println("Transaction is being rolled back.");
            entityManager.getTransaction().rollback();
         }
      } finally {
         if (entityManager != null) {
           // entityManager.close();
            //JPAUtil.shutdown();
         }
      }

      
   }
}
