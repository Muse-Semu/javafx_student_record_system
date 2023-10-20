package dataModels;

import java.util.Date;

public class StudentData {
	private int studId;
	private String fname;
	private String lname;
	private String sex;
	private String dept;
	private String phone;
	private String email;
	private Date registeredData;
	private int age;

	public StudentData(int studId, String fname, String lname, String sex, String dept, String phone, String email,
			Date registeredData, int age) {
		super();
		this.studId = studId;
		this.fname = fname;
		this.lname = lname;
		this.sex = sex;
		this.dept = dept;
		this.phone = phone;
		this.email = email;
		this.registeredData = registeredData;
		this.age = age;
	}

	public int getStudId() {
		return studId;
	}

	public void setStudId(int studId) {
		this.studId = studId;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getRegisteredData() {
		return registeredData;
	}

	public void setRegisteredData(Date registeredData) {
		this.registeredData = registeredData;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
