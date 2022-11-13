package com.raf.userservice.controller;

import com.raf.userservice.dto.*;
import com.raf.userservice.security.CheckSecurity;
import com.raf.userservice.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserServiceController {

    private UserService userService;

    public UserServiceController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @CheckSecurity(roles = {"admin"})
    public ResponseEntity<Page<UserDto>> getAllUsers(@RequestHeader("Authorization") String authorization, Pageable pageable) {
        return new ResponseEntity<>(userService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @CheckSecurity(roles = {"admin"})
    public ResponseEntity deleteUser(@RequestHeader("Authorization") String authorization, @PathVariable Long id){
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/client")
    public ResponseEntity<UserDto> saveUser(@RequestBody @Valid UserCreateDto userCreateDto) {
        return new ResponseEntity<>(userService.addClient(userCreateDto), HttpStatus.CREATED);
    }

    @PostMapping("/manager")
    public ResponseEntity<UserDto> saveManager(@RequestBody @Valid UserCreateDto userCreateDto) {
        return new ResponseEntity<>(userService.addManager(userCreateDto), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    @CheckSecurity(roles = {"admin", "client", "manager"})
    public ResponseEntity<UserDto> updateUser(@RequestBody @Valid UserUpdateDto userUpdateDto, @RequestHeader("Authorization") String authorization, @PathVariable Long id) {
        return new ResponseEntity<>(userService.update(userUpdateDto, id), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> loginUser(@RequestBody @Valid TokenRequestDto tokenRequestDto) {
        return new ResponseEntity<>(userService.login(tokenRequestDto), HttpStatus.OK);
    }

    @PutMapping(value = "/active/{id}")
    @CheckSecurity(roles = {"admin"})
    public ResponseEntity setActive(@RequestBody @Valid UserCreateActiveDto userCreateActiveDto, @RequestHeader("Authorization") String authorization, @PathVariable Long id) {
        userService.updateActive(userCreateActiveDto, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/registration/active/{email}")
    public ResponseEntity setActive(@PathVariable String email) {
        userService.confirmRegistration(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/discount/{userId}")
    public ResponseEntity<DiscountDto> getDiscount(@PathVariable Long userId){
        return new ResponseEntity<>(userService.getDiscount(userId), HttpStatus.OK);
    }

}
