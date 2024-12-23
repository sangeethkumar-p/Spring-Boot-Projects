package com.doc.swagger.SwaggerDocumentation.repository;

import com.doc.swagger.SwaggerDocumentation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {


    public User findByUserName(String username);
}
