package com.doc.swagger.SwaggerDocumentation.mapper;

import com.doc.swagger.SwaggerDocumentation.dto.EmployeeDto;
import com.doc.swagger.SwaggerDocumentation.entity.Employee;

public class EmployeeMapper {

    public static EmployeeDto mapToEmployeeDto(Employee employee){
        return new EmployeeDto(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getFullName(),
                employee.getEmail(),
                employee.getPassword());
    }

    public static Employee mapToEmployee(EmployeeDto employeeDto){
        return new Employee(
                employeeDto.getId(),
                employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getFullName(),
                employeeDto.getEmail(),
                employeeDto.getPassword());

    }
}
