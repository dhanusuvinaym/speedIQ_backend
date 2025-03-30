package com.example.demo.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.demo.entity.Questions;
import com.example.demo.service.QuestionsService;

//@CrossOrigin("http://localhost:3000")
//@CrossOrigin("http://speediq.com.s3-website.eu-north-1.amazonaws.com/")
@RestController
@RequestMapping("/api/questions")
public class QuestionsController {

	@Autowired
	private QuestionsService questionsService;

	@PostMapping("/upload")
	public String saveQuestion(@RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			return "Please upload a valid Excel file.";
		}

		try {
			List<Questions> questionsList = parseExcelFile(file);
			for (Questions question : questionsList) {
				questionsService.save(question);
			}
			return "File uploaded and questions saved successfully!";
		} catch (IOException e) {
			e.printStackTrace();
			return "Failed to process the file.";
		}
	}

	private List<Questions> parseExcelFile(MultipartFile file) throws IOException {
		List<Questions> questionsList = new ArrayList<>();
		Workbook workbook = new XSSFWorkbook(file.getInputStream());
		Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet

		for (Row row : sheet) {
			if (row.getRowNum() == 0)
				continue; // Skip header row

			Questions question = new Questions();
			question.setQuestion(getStringValue(row.getCell(0)));
			question.setOptionA(getStringValue(row.getCell(1)));
			question.setOptionB(getStringValue(row.getCell(2)));
			question.setOptionC(getStringValue(row.getCell(3)));
			question.setOptionD(getStringValue(row.getCell(4)));
			question.setCorrectOption(getStringValue(row.getCell(5)).charAt(0));
			question.setCreated_date(LocalDateTime.now());
			question.setUpdated_date(LocalDateTime.now());
			questionsList.add(question);
		}

		workbook.close();
		return questionsList;
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

	@GetMapping("/getRandom")
	public List<Questions> getRandom10Questions() {
		return questionsService.getRandom10Questions();
	}

	@DeleteMapping("/{id}")
	public String deleteQuestion(@PathVariable("id") int id) {
		questionsService.deleteQuestion(id);
		return "Question deleted successfully!";
	}

	@PutMapping("/{id}")
	public String updateQuestion(@PathVariable("id") int id, @RequestBody Questions question) {
		questionsService.updateQuestion(id, question);
		return "Question updated successfully!";
	}

	@PostMapping("/add")
	public String addQuestion(@RequestBody Questions q) {
		questionsService.save(q);
		return "Question added successfully";
	}

	@GetMapping("/getAll")
	public List<Questions> getAllQuestions() {
		return questionsService.getAllQuestions();
	}

	@GetMapping("/deleteAll")
	public void deleteAll() {
		questionsService.deleteAllrows();
	}
}
