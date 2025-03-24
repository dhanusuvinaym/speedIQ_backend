package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionsDTO {
	public int questionId;
	public String question;
	public String optionA;
	public String optionB;
	public String optionC;
	public String optionD;
}
