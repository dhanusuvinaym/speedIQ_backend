package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "login")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "username")
    private String username;
    
    @Column(name = "mobilenumber",length = 10)
    private String mobileNumber;

    @Column(name = "tokenid", nullable = false, length = 6, unique = true)
    private String tokenId;
    
    @Column(name = "valid", nullable = false)
    private boolean isvalid;
    
    @Column(name = "activity_date")
    private LocalDateTime activity_date;  
}
