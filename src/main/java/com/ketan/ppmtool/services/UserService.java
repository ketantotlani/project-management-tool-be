package com.ketan.ppmtool.services;

import com.ketan.ppmtool.domain.User;
import com.ketan.ppmtool.exceptions.UsernameAlreadyExistsException;
import com.ketan.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(User newUser){
        try {
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));

            newUser.setUsername(newUser.getUsername());

            newUser.setConfirmPassword("");
            return userRepository.save(newUser);
        }catch (Exception e){
            throw new UsernameAlreadyExistsException("Username '"+ newUser.getUsername()+ "' already exists");
        }
    }
}
