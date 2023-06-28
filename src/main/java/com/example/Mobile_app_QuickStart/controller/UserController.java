package com.example.Mobile_app_QuickStart.controller;


import com.example.Mobile_app_QuickStart.model.User;
import com.example.Mobile_app_QuickStart.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users") //  http://localhost:8084/users/
public class UserController {


      Logger logger = LoggerFactory.getLogger("Mobile_App");
    @Autowired
    UserService userService;




    @PostMapping("/newUsers")
    public List<User> addUsers(@RequestBody List<User> user ){
        logger.info("creating multiple users at a time...............");
        return  userService.addUsers(user);

    }

    @PostMapping("/newUser")
    @ResponseStatus(HttpStatus.CREATED)
    public User addSingleUser(@RequestBody User user){
        return userService.addSingleUser(user);
    }

//    @GetMapping("/getuser/{id}")
//   @ResponseStatus(HttpStatus.OK)
//    public User getUser(@PathVariable int id) {
//        logger.info("getting user details By id............................");
//        return userService.getUserById(id);
//    }

    @GetMapping("/getuser/{id}")
    public ResponseEntity getUser(@PathVariable int id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(user, HttpStatus.OK);
    }



    @GetMapping("/getusers")
    public List<User> getAllUsers(){
           logger.info("getting All users..........................");
        return userService.getAllUsers();
    }

    @PutMapping("/updateUser")
    public ResponseEntity updateUser(@RequestBody User user){
        logger.info("updating  user details.........................");
     User updateObj=   userService.updateUser(user);
        return new ResponseEntity(updateObj,HttpStatus.OK);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity updateUser(@PathVariable int id, @RequestParam String email){
        logger.info("Updating  users email id..........................");
         User user = userService.updateUser(id,email);
        if (user == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(user, HttpStatus.OK);
        }

    }


    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") int id){
        logger.info("deleting users by id ..........................");
        userService.deleteUser(id);
        return new ResponseEntity<String>("User deleted Successfully",HttpStatus.OK);

    }
}
