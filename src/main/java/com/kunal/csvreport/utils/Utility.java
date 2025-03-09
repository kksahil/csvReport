package com.kunal.csvreport.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import com.kunal.csvreport.domain.EmployeeDetail;

/*
 *  This Utility class contain several method related to the operation performed on the CSV record 
 *  such as : extracting each line of csv file (each employee record) and storing in Hash map for indexing
 *  , perform calculation such as calculating number of manager above employee, salary calculation 
 *  (min, max , average salary of an employee, increments, low-high salary status as well as salary difference
 */

public class Utility {

	/*
	 *  This method perform extraction of each line (employee record) from csv file and indexed it.
	 *  HashMap is use to hold the record in below format 
	 *  key = employee ID, values = instance of EmployeeDetail class to hold employee first name, last name, salary, 
	 *  manager Id.
	 *  indexing is beneficial for fast searching and to perform calculation on records
	 *  
	 *  Input : CSV file location
	 *  Output : return HashMap containing all record of csv file
	 */
	public Map generateIndexedRecord(String csvFilePath) {
		Map<Integer, EmployeeDetail> record = new HashMap();

		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(getClass().getResourceAsStream(csvFilePath)))) {

			String line;
			int lineNumber = 0;

			// Reading the CSV file line by line 
			while ((line = br.readLine()) != null) {
				// leaving first line which consist of headers
				if (lineNumber == 0) {
					lineNumber++;
					continue;
				}
				String[] row = line.split(",");
				EmployeeDetail empRow = new EmployeeDetail();
				empRow.setFirstName(row[1]);
				empRow.setLastName(row[2]);
				empRow.setSalary(Float.parseFloat(row[3]));
				if (row.length > 4)
					empRow.setManagerId(Integer.parseInt(row[4]));
				
				// putting each row in hashmap
				record.put(Integer.parseInt(row[0]), empRow);
			}

		} catch (FileNotFoundException e) {
			System.out.println("CSV File Not Found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Input Output Exception");
			e.printStackTrace();
		}

		return record;

	}

	/*
	 *  This method calculate the numbers of manager above each employee and 
	 *  also to calculate combined salary of employee who reports to his direct manager
	 *  
	 *  Input : HashMap containing record which is extracted through the method "generateIndexedRecord()"
	 *  Output : return the HashMap with updated record.
	 */
	
	public Map calculateManager(Map<Integer, EmployeeDetail> record) {

		int empDtl = 0;
		int managerCount = 1;
		int subordinateCount = 0;
		float combinedSalary = 0;

		for (Map.Entry<Integer, EmployeeDetail> singleMap : record.entrySet()) {
			empDtl = singleMap.getValue().getManagerId();
			
			// this while loop is use to traverse to each manager ID upto CEO who doesn't have any manager 
			// above him (i.e. manager ID = 0) and count the number of managers above each employee 
			while (empDtl != 0) {
				managerCount++;				
				empDtl = record.get(empDtl).getManagerId();
			}			
			singleMap.getValue().setNumberOfManagerAbove(managerCount - 1);	
			//updating record with total number of managers above this employee
			record.put(singleMap.getKey(), singleMap.getValue());

			// find the manager of this employee and update his combinedSubOrdinateSalary column with employee salary
			// add in it... also increase the manager's numberOfSubordinate column by 1 as employee reports 
			// to this manager directly
			if (singleMap.getValue().getManagerId() != 0) {
				combinedSalary = record.get(singleMap.getValue().getManagerId()).getCombinedSubOrdinatesSalary()
						+ singleMap.getValue().getSalary();
				subordinateCount = record.get(singleMap.getValue().getManagerId()).getNumberOfSubordinates() + 1;
				record.get(singleMap.getValue().getManagerId()).setCombinedSubOrdinatesSalary(combinedSalary);
				record.get(singleMap.getValue().getManagerId()).setNumberOfSubordinates(subordinateCount);
			}
			managerCount = 1;
			combinedSalary = 0;
			subordinateCount = 0;
		}

		return record;
	}

	/*
	 * This method is use to calculate average salary of total subordinates for each manager 
	 * by dividing the combinedSubordinateSalry column with numberOfSubordinate column,
	 * get the minimum and maximum salary range (which manager should earn) by
	 * calculating 20% of average salary and 50% of average salary
	 * also, calculating, whether employee earning lower than the mininum range or 
	 * higher than the maximum range, or in between the low-high range and update the lowHighMiddle column 
	 * of manager record with status "high", "low", "betwenn range", "not a amanager"
	 * also calculating the salary difference and updating the column accordingly
	 * 
	 * Input : HashMap containing all employee records
	 * Output : return update Hashmap of records
	 */
	public Map calculateSalary(Map<Integer, EmployeeDetail> record) {

		float combinedAverageSubordinateSalary = 0, calculatedMinSalary = 0, calculatedMaxSalary = 0,
				combinedAverageMaxSalary = 0, salaryDiff = 0;

		for (Map.Entry<Integer, EmployeeDetail> empDetail : record.entrySet()) {

			// if number of subordinates is greater than 0 then he is a manager otherwise he is not 
			if (empDetail.getValue().getNumberOfSubordinates() > 0) {
				combinedAverageSubordinateSalary = empDetail.getValue().getCombinedSubOrdinatesSalary()
						/ empDetail.getValue().getNumberOfSubordinates();
				calculatedMinSalary = (float) (combinedAverageSubordinateSalary * .2)
						+ combinedAverageSubordinateSalary;
				empDetail.getValue().setMinSalaryIncrement(calculatedMinSalary);
				calculatedMaxSalary = (float) (combinedAverageSubordinateSalary * .5)
						+ combinedAverageSubordinateSalary;
				empDetail.getValue().setMaxSalaryIncrement(calculatedMaxSalary);
				
				if (empDetail.getValue().getSalary() <= calculatedMinSalary) {
					empDetail.getValue().setEarningLowHighMiddle("low");
					salaryDiff = calculatedMinSalary - empDetail.getValue().getSalary();
					empDetail.getValue().setSalaryDifference(salaryDiff);
				} else if (empDetail.getValue().getSalary() >= calculatedMaxSalary) {
					empDetail.getValue().setEarningLowHighMiddle("High");
					salaryDiff = empDetail.getValue().getSalary() - calculatedMaxSalary;
					empDetail.getValue().setSalaryDifference(salaryDiff);
				} else {
					empDetail.getValue().setEarningLowHighMiddle("Between Range");
				}

				record.put(empDetail.getKey(), empDetail.getValue());
				
			} else {
				empDetail.getValue().setEarningLowHighMiddle("Not a Manager");
			}
		}

		return record;
	}

}
