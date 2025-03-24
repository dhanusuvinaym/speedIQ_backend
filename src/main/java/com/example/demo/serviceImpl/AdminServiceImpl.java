package com.example.demo.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Admin;
import com.example.demo.repository.AdminRepository;
import com.example.demo.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Admin createUser(Admin user) {
        user.setCreatedate(LocalDateTime.now());
        user.setUpdatedate(LocalDateTime.now());
        return adminRepository.save(user);
    }

    @Override
    public Admin updateUser(int id, Admin partialUser) {
        Optional<Admin> existingUserOpt = adminRepository.findById(id);
        if (existingUserOpt.isPresent()) {
        	Admin existingUser = existingUserOpt.get();
            if (partialUser.getUsername() != null) {
                existingUser.setUsername(partialUser.getUsername());
            }
            if (partialUser.getPassword() != null) {
                existingUser.setPassword(partialUser.getPassword());
            }
            existingUser.setUpdatedate(LocalDateTime.now());
            return adminRepository.save(existingUser);
        }
        throw new RuntimeException("User not found!");
    }

    @Override
    public void deleteUser(int id) {
        if (adminRepository.existsById(id)) {
        	adminRepository.deleteById(id);
        } else {
            throw new RuntimeException("User not found!");
        }
    }

    @Override
    public Admin validateUserDetails(String username, String password) {
       return adminRepository.getAdminDetailsByUsingUsernameAndPassword(username, password);
    }

	@Override
	public List<Admin> getAllAdmins() {
		return adminRepository.findAll();
	}
}
