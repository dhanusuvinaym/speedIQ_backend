package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
	public Integer id;
	public String username;
	public String tokenId;
	public boolean isvalid;
	public LocalDateTime activity_date;
	public String jwtToken;
}