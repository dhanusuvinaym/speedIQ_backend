package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Admin;

public interface AdminService {
    Admin createUser(Admin user);
    Admin updateUser(int id, Admin partialUser);
    void deleteUser(int id);
    Admin validateUserDetails(String username, String password);
    List<Admin> getAllAdmins();
}
