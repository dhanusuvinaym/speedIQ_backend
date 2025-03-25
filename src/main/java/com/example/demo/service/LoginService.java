package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Login;

public interface LoginService {

	void createUser(Login l);
	
	void updateCredentials(int id,Login l);
	
	Login getUserWithToken(String token);
	
	void deleteUser(int id);
	
	List<Login> getAllLoginDetails();
	
	void deleteAllrows();
}
