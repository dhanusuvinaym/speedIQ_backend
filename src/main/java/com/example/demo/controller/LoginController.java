package com.example.demo.controller;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.LoginDTO;
import com.example.demo.entity.Login;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.LoginService;

import lombok.extern.slf4j.Slf4j;

//@CrossOrigin("http://localhost:3000")
@CrossOrigin("http://speediqfrontend.s3-website-ap-southeast-2.amazonaws.com/")
@RestController
@RequestMapping("/api/login")
@Slf4j
public class LoginController {

	@Autowired
	private LoginService loginService;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/create")
	public ResponseEntity<?> createUser(@RequestBody Login login) throws SQLIntegrityConstraintViolationException {
		Login l = new Login();
		l.setTokenId(login.getTokenId());
		l.setIsvalid(true);
		try {
			loginService.createUser(l);
			return ResponseEntity.ok("User created successfully!");
		} catch (DataIntegrityViolationException e) {
			// Log the error for debugging purposes
//	        System.err.println("Error creating user: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("Error: Duplicate entry or constraint violation. Please provide unique values!");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An unexpected error occurred. Please try again later.");
		}
	}

	@PostMapping("/upload")
	public String saveloginDetails(@RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			return "Please upload a valid Excel file.";
		}

		try {
			List<Login> loginList = parseExcelFile(file);
			for (Login l : loginList) {
				loginService.createUser(l);
			}
			return "File uploaded and questions saved successfully!";
		} catch (IOException e) {
			e.printStackTrace();
			return "Failed to process the file.";
		}
	}

	private List<Login> parseExcelFile(MultipartFile file) throws IOException {
		List<Login> loginList = new ArrayList<>();
		Workbook workbook = new XSSFWorkbook(file.getInputStream());
		Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet

		for (Row row : sheet) {
			if (row.getRowNum() == 0)
				continue; // Skip header row

			Login l = new Login();
			l.setTokenId(getStringValue(row.getCell(0)));
			l.setIsvalid(true);
			loginList.add(l);
		}

		workbook.close();
		return loginList;
	}

	// Utility method to handle different cell types
	private String getStringValue(Cell cell) {
		if (cell == null) {
			return "";
		}
		if (cell.getCellType() == CellType.STRING) {
			return cell.getStringCellValue().trim();
		} else if (cell.getCellType() == CellType.NUMERIC) {
			return String.valueOf((int) cell.getNumericCellValue()); // Convert numeric values to string
		}
		return "";
	}

	@PutMapping("/update/{id}")
	public String updateCredentials(@PathVariable("id") int id, @RequestBody Login login) {
		log.info("Login  = {} ", login);
		loginService.updateCredentials(id, login);
		return "User credentials updated successfully!";
	}

	@PostMapping("/validate")
	public ResponseEntity<LoginDTO> getUser(@RequestBody LoginDTO l) {
		try {
			Login login = loginService.getUserWithToken(l.getTokenId());
			if (login == null) {
				throw new RuntimeException("Invalid Token ID");
			}
			login.setUsername(l.getUsername());
			login.setMobileNumber(l.getMobilenumber());
			loginService.updateCredentials(login.getId(), login);

			LoginDTO res = new LoginDTO();
			res.setId(login.getId());
			res.setIsvalid(login.isIsvalid());
			res.setTokenId(login.getTokenId());
			res.setUsername(l.getUsername());
			res.setMobilenumber(l.getMobilenumber());
			res.setJwtToken(jwtUtil.generateToken(login.getTokenId()));

			return ResponseEntity.ok(res);
		} catch (Exception e) {
			log.error("Error validating user", e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	@DeleteMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") int id) {
		loginService.deleteUser(id);
		return "User deleted successfully!";
	}

	@GetMapping("/getAll")
	public List<Login> getAllLoginDetails() {
		return loginService.getAllLoginDetails();
	}

	@GetMapping("/deleteAll")
	public void deleteAll() {
		loginService.deleteAllrows();
	}
}
