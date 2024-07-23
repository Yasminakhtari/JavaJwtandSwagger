package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.entity.UserFile;
import jakarta.mail.MessagingException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    User saveUser(User user) throws MessagingException;

    List<User> findAllUser();

    UserFile save(String fileName, MultipartFile file);
}
