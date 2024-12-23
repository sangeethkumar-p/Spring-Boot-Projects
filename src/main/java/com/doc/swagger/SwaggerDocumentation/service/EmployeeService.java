package com.doc.swagger.SwaggerDocumentation.service;

import com.doc.swagger.SwaggerDocumentation.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {

    public EmployeeDto addNewEmployee(EmployeeDto employeeDto);

    public List<EmployeeDto> getAllEmployees();

    public EmployeeDto getEmployeeById(Long id);

    public EmployeeDto updateEmployeeById(EmployeeDto employeeDto, Long id);

    public void deleteEmployeeById(Long id);

}
