package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
	
	@Query(value="select * from admin where username = :username and password = :password",nativeQuery=true)
	Admin getAdminDetailsByUsingUsernameAndPassword(@Param("username")String username,@Param("password")String password);
}
