package dataModels;

import java.util.Date;

public class DepartmentData {
	private int depId, depCapacity;
	private String depName;
	private Date reDate;

	public DepartmentData(int depId, int depCapacity, String depName, Date reDate) {
		super();
		this.depId = depId;
		this.depCapacity = depCapacity;
		this.depName = depName;
		this.reDate = reDate;
	}

	public int getDepId() {
		return depId;
	}

	public void setDepId(int depId) {
		this.depId = depId;
	}

	public int getDepCapacity() {
		return depCapacity;
	}

	public void setDepCapacity(int depCapacity) {
		this.depCapacity = depCapacity;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public Date getReDate() {
		return reDate;
	}

	public void setReDate(Date reDate) {
		this.reDate = reDate;
	}

}
