package io.employeePayroll;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import io.employeePayroll.EmployeePayrollService.IOService;;

public class EmployeePayrollServiceTest {
	
	@Test
	public void given3Employees_WrttenToFile_ShouldMatchEmployeeEntries() {
		EmployeePayrollData[] arrayEmployee = {new EmployeePayrollData(1, "Chandler", 1000000),
											   new EmployeePayrollData(2, "Monica", 5000000),
											   new EmployeePayrollData(3, "Ross", 3000000)};
		
		EmployeePayrollService employeePayrollService = new EmployeePayrollService(Arrays.asList(arrayEmployee));
		employeePayrollService.writeEmployeePayrollData(IOService.FILE_IO);
		employeePayrollService.printData(IOService.FILE_IO);
		long entries = employeePayrollService.countEntries(IOService.FILE_IO);
		Assert.assertEquals(3, entries);
	}
}
