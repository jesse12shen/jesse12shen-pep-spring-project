package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;
//^ automatically generated

// import java.util.Optional;

import com.example.entity.Account;

//  custom queries are considered JPARepo only
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{
//  Account insertAccount(String username, String password); // INSERT may have been replaced with save, but you should have a "find by..." query to complete INSERT's functionality
Account  findByUsername(String username); // Could write a custom @query to return a username string only
}
