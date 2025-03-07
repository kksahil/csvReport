package com.kunal.csvreport;

import java.util.HashMap;
import java.util.Map;
import com.kunal.csvreport.domain.EmployeeDetail;
import com.kunal.csvreport.utils.Utility;

public class Main {
	public static void main(String args[]) {

		Utility utility = new Utility();
		Map<Integer, EmployeeDetail> record = new HashMap<Integer, EmployeeDetail>();
		Map<Integer, EmployeeDetail> updatedRecord = new HashMap<Integer, EmployeeDetail>();
		Map<Integer, EmployeeDetail> calculatedSalary = new HashMap<Integer, EmployeeDetail>();
		//
	  System.out.println(System.getProperty("user.dir"));
		
		record = utility.generateIndexedRecord("/employeeDetail.csv");
		updatedRecord = utility.calculateManager(record);
		calculatedSalary = utility.calculateSalary(updatedRecord);

		System.out.println("\u001B[33m"
				+ "Emp_ID  First_Name  Last_Name   Salary  Mgr_ID  No.Of_Mgr_Abv  Tot_SubOrdnt_Sal  No.Of_SubOrdnt  "
				+ "Earning_Low_High  Sal_Diff  Min_Sal  Max_Sal " + "\u001B[0m");
		for (Map.Entry<Integer, EmployeeDetail> map : calculatedSalary.entrySet())
			// System.out.println(map.getValue().getAllValues());
			System.out.printf("%6s%12s%11s%9s%8s%15s%18s%16s%18s%10s%9s%9s\n", map.getKey(),
					map.getValue().getFirstName(), map.getValue().getLastName(), map.getValue().getSalary(),
					map.getValue().getManagerId(), map.getValue().getNumberOfManagerAbove(),
					map.getValue().getCombinedSubOrdinatesSalary(), map.getValue().getNumberOfSubordinates(),
					map.getValue().getEarningLowHighMiddle(), map.getValue().getSalaryDifference(),
					map.getValue().getMinSalaryIncrement(), map.getValue().getMaxSalaryIncrement());

	}
}
