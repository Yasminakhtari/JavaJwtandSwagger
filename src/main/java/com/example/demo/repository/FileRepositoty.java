package com.example.demo.repository;

import com.example.demo.entity.UserFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepositoty extends JpaRepository<UserFile,Long> {
}
