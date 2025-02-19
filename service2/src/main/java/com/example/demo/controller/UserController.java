package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){ this.userService = userService; }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable UUID id) throws Exception{
        return new ResponseEntity<>(userService.getUser(id),HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) throws Exception{
        return new ResponseEntity<>(userService.createUser(userDTO), HttpStatus.CREATED);
    }
}
