package com.doc.swagger.SwaggerDocumentation.repository;

import com.doc.swagger.SwaggerDocumentation.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
