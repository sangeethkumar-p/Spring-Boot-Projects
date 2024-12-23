package com.doc.swagger.SwaggerDocumentation.service;

import com.doc.swagger.SwaggerDocumentation.dto.EmployeeDto;
import com.doc.swagger.SwaggerDocumentation.entity.Employee;
import com.doc.swagger.SwaggerDocumentation.exceptions.EmployeeNotFoundException;
import com.doc.swagger.SwaggerDocumentation.mapper.EmployeeMapper;
import com.doc.swagger.SwaggerDocumentation.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeDto addNewEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
       return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll().stream().map(EmployeeMapper::mapToEmployeeDto).collect(Collectors.toList());
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {

        Optional<Employee> employeeOptional = employeeRepository.findById(id);

        if(employeeOptional.isEmpty()) {
            throw new EmployeeNotFoundException("Employee with ID " + id + " does not exists.");
        }

        return EmployeeMapper.mapToEmployeeDto(employeeOptional.get());
    }

    @Override
    public EmployeeDto updateEmployeeById(EmployeeDto employeeDto, Long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);

        if(employeeOptional.isEmpty()) {
            throw new EmployeeNotFoundException("Employee with ID " + id + " does not exists.");
        }

        Employee existingEmployee = employeeOptional.get();
        existingEmployee.setFirstName(employeeDto.getFirstName());
        existingEmployee.setLastName(employeeDto.getLastName());
        existingEmployee.setFullName(employeeDto.getFullName());
        existingEmployee.setEmail(employeeDto.getEmail());

        Employee updatedEmployee = employeeRepository.save(existingEmployee);

        return EmployeeMapper.mapToEmployeeDto(updatedEmployee);
    }

    @Override
    public void deleteEmployeeById(Long id) {

        Optional<Employee> employeeOptional = employeeRepository.findById(id);

        if(employeeOptional.isEmpty()) {
            throw new EmployeeNotFoundException("Employee with ID " + id + " does not exists.");
        }
        employeeRepository.deleteById(id);
    }
}
