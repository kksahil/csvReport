package com.kunal.csvreport.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import com.kunal.csvreport.domain.EmployeeDetail;

public class Utility {

	public Map generateIndexedRecord(String csvFilePath) {
		Map<Integer, EmployeeDetail> record = new HashMap();
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(csvFilePath))))  {
				
			String line;
			int lineNumber = 0;

			// Reading the CSV file line by line
			while ((line = br.readLine()) != null) {
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
				;
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

	public Map calculateManager(Map<Integer, EmployeeDetail> record) {

		int empDtl = 0;
		Map<Integer, EmployeeDetail> updatedRecord = new HashMap<Integer, EmployeeDetail>();
		int managerCount = 1;
		int subordinateCount = 0;
		float combinedSalary = 0;

		for (Map.Entry<Integer, EmployeeDetail> singleMap : record.entrySet()) {
			empDtl = singleMap.getValue().getManagerId();
			// System.out.println("Manager ID " + empDtl);

			while (empDtl != 0) {
				managerCount++;
				// empDtl = getManagerId(empDtl, record).getManagerId();
				empDtl = record.get(empDtl).getManagerId();
			}
			// managerCount-1 == -1? managerCount=0:0;
			singleMap.getValue().setNumberOfManagerAbove(managerCount - 1);

			// updatedRecord.put(singleMap.getKey(), singleMap.getValue());
			record.put(singleMap.getKey(), singleMap.getValue());
			// calculateSalary(singleMap);

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

		// return updatedRecord;
		return record;
	}


	public Map calculateSalary(Map<Integer, EmployeeDetail> record) {

		float combinedAverageSubordinateSalary = 0, calculatedMinSalary = 0, calculatedMaxSalary = 0,
				combinedAverageMaxSalary = 0, salaryDiff = 0;

		for (Map.Entry<Integer, EmployeeDetail> empDetail : record.entrySet()) {

			if (empDetail.getValue().getNumberOfSubordinates() > 0) {
				combinedAverageSubordinateSalary = empDetail.getValue().getCombinedSubOrdinatesSalary()
						/ empDetail.getValue().getNumberOfSubordinates();
				calculatedMinSalary = (float) (combinedAverageSubordinateSalary * .2)
						+ combinedAverageSubordinateSalary;
				empDetail.getValue().setMinSalaryIncrement(calculatedMinSalary);
				calculatedMaxSalary = (float) (combinedAverageSubordinateSalary * .5)
						+ combinedAverageSubordinateSalary;
				empDetail.getValue().setMaxSalaryIncrement(calculatedMaxSalary);
				// combinedAverageMaxSalary = (float) (combinedAverageSubordinateSalary * .5) +
				// combinedAverageSubordinateSalary;
				// System.out.println(combinedAverageSubordinateSalary)

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
