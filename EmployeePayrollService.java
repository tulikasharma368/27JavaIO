package io.employeePayroll;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeePayrollService {
	public enum IOService {
		CONSOLE_IO, FILE_IO, DB_IO, REST_IO
		};
		
	private List<EmployeePayrollData> employeePayrollList;
	
	public EmployeePayrollService(List<EmployeePayrollData> employeePayrollList) {
		this.employeePayrollList = employeePayrollList;
	}
	
	public static void main(String[] args) {
		List<EmployeePayrollData> employeePayrollList = new ArrayList<EmployeePayrollData>();
		EmployeePayrollService employeePayrollService = new EmployeePayrollService(employeePayrollList);
		Scanner consoleInputReader = new Scanner(System.in);
		employeePayrollService.readEmployeeData(consoleInputReader);
		employeePayrollService.writeEmployeePayrollData(null);
	}

	private void readEmployeeData(Scanner consoleInputReader) {
		System.out.println("Enter employee Id");
		int id = consoleInputReader.nextInt();
		System.out.println("Enter employee name");
		String name = consoleInputReader.next();
		System.out.println("Enter employee salary");
		double salary = consoleInputReader.nextDouble();
		employeePayrollList.add(new EmployeePayrollData(id,name,salary));
	}
	

	public void writeEmployeePayrollData(IOService fileIo) {
		if (fileIo.equals(IOService.CONSOLE_IO))
			System.out.println("\nWriting Employee Paroll Roaster to Console\n" + employeePayrollList);
		else if (fileIo.equals(IOService.FILE_IO))
			new EmployeePayrollFileService().writeData(employeePayrollList);
	}

	public void printData(IOService fileIo) {
		if (fileIo.equals(IOService.FILE_IO))
			new EmployeePayrollFileService().printData();
	}

	public long countEntries(IOService fileIo) {
		if (fileIo.equals(IOService.FILE_IO))
			return new EmployeePayrollFileService().countEntries();
		return 0;
	}
	
}

