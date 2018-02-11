/**
 * 
 */
package com.app.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author imdadareeph
 *
 */
@Entity
@Table(name="EMP_DET", schema="ENTITYDATA")
public class EmpDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  @Column(name="ID")
  private Long id;
  @Column(name="EMP_NO")
  private Long empNumber;
  @Column(name="FULL_NAME")
  private String fullName;
  @Column(name="EMAIL_ADDRESS")
  private String emailAddress;
  
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getEmpNumber() {
		return empNumber;
	}
	public void setEmpNumber(Long empNumber) {
		this.empNumber = empNumber;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	/*@Override
	public String toString() {
		return "EmpDetails [id=" + id + ", empNumber=" + empNumber + ", fullName=" + fullName + ", emailAddress="
				+ emailAddress + "]";
	}*/
	

}
