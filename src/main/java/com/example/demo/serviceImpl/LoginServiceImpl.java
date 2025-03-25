package com.example.demo.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Login;
import com.example.demo.repository.LoginRepository;
import com.example.demo.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginRepository loginRepository;

	// Create a new user
	@Override
	public void createUser(Login l) {
		loginRepository.save(l);
	}

	// Update user credentials
	@Override
	public void updateCredentials(int id, Login l) {
		Optional<Login> existingUser = loginRepository.findById(id);
		if (existingUser.isPresent()) {
			Login updatedUser = existingUser.get();
			updatedUser.setMobileNumber(l.getMobileNumber());
			updatedUser.setTokenId(l.getTokenId());
			updatedUser.setActivity_date(l.getActivity_date());
			updatedUser.setIsvalid(l.isIsvalid());
			loginRepository.save(updatedUser);
		}
	}

	// Get a user by username and token
	@Override
	public Login getUserWithToken(String token) {
		return loginRepository.findByTokenId(token).orElse(null);
	}

	// Delete a user by ID
	@Override
	public void deleteUser(int id) {
		loginRepository.deleteById(id);
	}

	@Override
	public List<Login> getAllLoginDetails() {
		// TODO Auto-generated method stub
		return loginRepository.findAll();
	}

	@Override
	public void deleteAllrows() {
		// TODO Auto-generated method stub
		loginRepository.deleteAll();
	}

}
