package com.example.demo.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.Login;

public interface LoginRepository extends JpaRepository<Login, Integer> {

	Optional<Login> findByTokenId(String tokenId);
}
