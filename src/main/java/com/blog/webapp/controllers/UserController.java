package com.blog.webapp.controllers;

import com.blog.webapp.payloads.ApiResponse;
import com.blog.webapp.payloads.UserDto;
import com.blog.webapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("")
    public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody UserDto userDto){

       this.userService.createUser(userDto);
        return new ResponseEntity<>(new ApiResponse("User Created Successfully", true),HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto dto,@PathVariable Integer userId){

        UserDto userDto = userService.updateUser(dto, userId);

        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId){

        userService.deleteUser(userId);
        return ResponseEntity.ok(new ApiResponse("User Deleted Successfully",true));
    }

    @GetMapping("")
    public ResponseEntity<Map<String,List<UserDto>>> getAllUsers(){

        return ResponseEntity.ok(Map.of("users",userService.getAllUsers()));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Integer userId){

        return ResponseEntity.ok(userService.getUserById(userId));
    }
}
