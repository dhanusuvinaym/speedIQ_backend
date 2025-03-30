package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AdminDTO;
import com.example.demo.entity.Admin;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.AdminService;


//@CrossOrigin("http://speediq.com.s3-website.eu-north-1.amazonaws.com/")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/create")
	public ResponseEntity<Admin> createUser(@RequestBody Admin user) {
		Admin createdUser = adminService.createUser(user);
		return ResponseEntity.ok(createdUser);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Admin> updateUser(@PathVariable("id") int id, @RequestBody Admin partialUser) {
		Admin updatedUser = adminService.updateUser(id, partialUser);
		return ResponseEntity.ok(updatedUser);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") int id) {
		adminService.deleteUser(id);
		return ResponseEntity.ok("User deleted successfully!");
	}

	@PostMapping("/validate")
	public ResponseEntity<AdminDTO> validateUser(@RequestParam("username") String username,
			@RequestParam("password") String password) {
		Admin admin = adminService.validateUserDetails(username, password);
		AdminDTO adminDto = new AdminDTO();
		if (admin != null) {
			adminDto.setId(admin.getId());
			adminDto.setJwtToken(jwtUtil.generateToken(password));
			adminDto.setUsername(username);
			adminDto.setPassword(password);
			return ResponseEntity.ok(adminDto);
		} else {
			return null;
		}
	}
	
	@GetMapping("/getAll")
	public List<Admin> getAllAdminDetails(){
		return adminService.getAllAdmins();
	}
}
