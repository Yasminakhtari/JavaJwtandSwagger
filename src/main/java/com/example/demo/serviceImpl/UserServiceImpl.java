package com.example.demo.serviceImpl;

import com.example.demo.entity.User;
import com.example.demo.entity.UserFile;
import com.example.demo.repository.FileRepositoty;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.EmailService;
import com.example.demo.service.FileService;
import com.example.demo.service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private EmailService emailService;
    @Autowired
    private FileService fileService;
    @Autowired
    private FileRepositoty fileRepositoty;
    @Override
    public User saveUser(User user) throws MessagingException{
        User checkEmail=repository.findByEmail(user.getEmail());
            if (checkEmail!=null) {
              throw new EmailAlreadyExistsException("Email already exist");
            }
        user.setBitDeletedFlag(user.getBitDeletedFlag() == null ? (byte) 0 : user.getBitDeletedFlag());
        user.setCreatedBy(user.getCreatedBy() == null  ? "1" : user.getCreatedBy());
        user.setUpdatedBy(user.getUpdatedBy() == null ? "1" : user.getUpdatedBy());
        user.setPassword(encoder.encode(user.getPassword()));
        User savedUser=repository.save(user);
        emailService.sendEmail(user.getEmail(),"User Created",user.getUserName(),"Thank you"
        );
        return savedUser;
    }

    @Override
    public List<User> findAllUser() {
        return repository.findAll();
    }

    @Override
    public UserFile save(String fileName, MultipartFile file) {
        UserFile varFile=new UserFile();
        //resumes indicates the subfolder of main folder
        String resumePath = fileService.storeFile(file, "resumes");
        varFile.setFilePath(resumePath);
        varFile.setFileName(fileName);
        fileRepositoty.save(varFile);
        return varFile;
    }

    public class EmailAlreadyExistsException extends RuntimeException {
        public EmailAlreadyExistsException(String message) {
            super(message);
        }
    }

}
