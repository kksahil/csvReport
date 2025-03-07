package com.kunal.csvreport.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import com.kunal.csvreport.domain.EmployeeDetail;

public class UtilityTest {

	private Map record = new HashMap<Integer, EmployeeDetail>();
	private Utility utility = new Utility();

	@Test
	void testCsvIndexing() {
		// assertEquals(record, utility.generateIndexedRecord("/employeeDetail.csv"));
		assertInstanceOf(record.getClass(), utility.generateIndexedRecord("/employeeDetail.csv"));
	}

	@Test
	void testCalculateManager() {
		record = utility.generateIndexedRecord("/employeeDetail.csv");
		assertInstanceOf(record.getClass(), utility.calculateManager(record));
	}

	@Test
	void testCalculateSalary() {
		record = utility.generateIndexedRecord("/employeeDetail.csv");
		assertInstanceOf(record.getClass(), utility.calculateSalary(record));

	}

}
