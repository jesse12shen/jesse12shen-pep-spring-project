package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{ 
    // interface extending to repository verifies the other components and makes it a bean
    // Integer deleteById(int id); //idk how to make it update with a string via override then without a custom query
    // @Query("DELETE FROM message where messageId = :id")
    // Integer deleteById_i(@Param("id") int id); // seems to cause dependency issues if I do it this way
}
