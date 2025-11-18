package com.autisheimer.journalApp.controller;

import com.autisheimer.journalApp.api.Response.WeatherResponse;
import com.autisheimer.journalApp.entity.User;
import com.autisheimer.journalApp.repository.UserRepository;
import com.autisheimer.journalApp.service.UserService;
import com.autisheimer.journalApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WeatherService weatherService;

    //    @GetMapping
    //    public List<User> getAllUsers(){
    //        return userService.getAll();
    //    }

    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User userInDb = userService.findByUsername(username);
        userInDb.setUsername(user.getUsername());
        userInDb.setPassword(user.getPassword());
        userService.saveNewUser(userInDb);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUsername(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping()
    public ResponseEntity<?> greetUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        WeatherResponse weatherResponse = weatherService.getWeather("Lucknow");
        String weather = "";
        if (weatherResponse != null){
            weather = weatherResponse.getCurrent().getTempC()+" C";
        }

        return new ResponseEntity<>("hi "+authentication.getName()+"\tTemperature at Lucknow is : \n" + weather ,HttpStatus.OK);
    }

}