package com.kunal.csvreport.domain;

public class EmployeeDetail {
	
	String firstName;
	String lastName;
	float salary = 0;
	int managerId = 0;
	int numberOfManagerAbove = 0;
	float combinedSubOrdinatesSalary = 0;
	int numberOfSubordinates = 0;	
	String earningLowHighMiddle;
	float minSalaryIncrement = 0;
	float maxSalaryIncrement = 0;
	float salaryDifference = 0;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public float getSalary() {
		return salary;
	}
	public void setSalary(float salary) {
		this.salary = salary;
	}
	public int getManagerId() {
		return managerId;
	}
	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}
	public int getNumberOfManagerAbove() {
		return numberOfManagerAbove;
	}
	public void setNumberOfManagerAbove(int numberOfManagerAbove) {
		this.numberOfManagerAbove = numberOfManagerAbove;
	}
	public float getCombinedSubOrdinatesSalary() {
		return combinedSubOrdinatesSalary;
	}
	public void setCombinedSubOrdinatesSalary(float combinedSubOrdinatesSalary) {
		this.combinedSubOrdinatesSalary = combinedSubOrdinatesSalary;
	}
	public int getNumberOfSubordinates() {
		return numberOfSubordinates;
	}
	public void setNumberOfSubordinates(int numberOfSubordinates) {
		this.numberOfSubordinates = numberOfSubordinates;
	}
	public String getEarningLowHighMiddle() {
		return earningLowHighMiddle;
	}
	public void setEarningLowHighMiddle(String earningLowHighMiddle) {
		this.earningLowHighMiddle = earningLowHighMiddle;
	}	
	public float getMinSalaryIncrement() {
		return minSalaryIncrement;
	}
	public void setMinSalaryIncrement(float minSalaryIncrement) {
		this.minSalaryIncrement = minSalaryIncrement;
	}
	public float getMaxSalaryIncrement() {
		return maxSalaryIncrement;
	}
	public void setMaxSalaryIncrement(float maxSalaryIncrement) {
		this.maxSalaryIncrement = maxSalaryIncrement;
	}
	public float getSalaryDifference() {
		return salaryDifference;
	}
	public void setSalaryDifference(float salaryDifference) {
		this.salaryDifference = salaryDifference;
	}
	
	public String getAllValues() {
		return this.getFirstName() + "" + this.getLastName() + " " + this.getSalary() + " " + this.getManagerId() + " " + this.getNumberOfManagerAbove() + " " + this.getCombinedSubOrdinatesSalary()
		 + " " + this.getNumberOfSubordinates() + " " + this.getEarningLowHighMiddle() + " " +  this.getSalaryDifference() + " " + this.getMinSalaryIncrement() + " " + this.getMaxSalaryIncrement();
	}

}
