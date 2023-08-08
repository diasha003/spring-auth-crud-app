package com.example.demo.rest;

import com.example.demo.entities.User;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserRestController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/{id}/id", produces = "application/json")
    public User findById(@PathVariable("id") Long id) throws ChangeSetPersister.NotFoundException {
        return userService.findById(id).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
    }

    @GetMapping(path="/all", produces = "application/json")
    public List<User> findAll(){
        return userService.findAll();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public User createUser(@RequestBody User user){
        userService.createOrUpdate(user);
        return user;
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public User updateUser(@RequestBody User user){
        userService.createOrUpdate(user);
        return user;
    }

    @DeleteMapping("{id}/id")
    public void deleteById(@PathVariable("id") Long id){
        userService.deleteById(id);
    }

    @ExceptionHandler
    public ResponseEntity<String> notFoundExceptionHandler(ChangeSetPersister.NotFoundException e){
        return new ResponseEntity<>("entity not found", HttpStatus.NOT_FOUND);

     }
}
