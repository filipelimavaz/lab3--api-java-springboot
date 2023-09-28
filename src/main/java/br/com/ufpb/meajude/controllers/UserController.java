package br.com.ufpb.meajude.controllers;

import br.com.ufpb.meajude.dtos.user.UserDTO;
import br.com.ufpb.meajude.dtos.user.UserRegistrationDTO;
import br.com.ufpb.meajude.dtos.user.UserUpdateDTO;
import br.com.ufpb.meajude.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        return new ResponseEntity<>(userService.registerUser(userRegistrationDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserDTO> returnUser(@PathVariable String email) {
        return new ResponseEntity<>(userService.returnUser(email), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> returnUserList(){
        return new ResponseEntity<List<UserDTO>>(userService.returnUserList(), HttpStatus.OK);
    }

    @PatchMapping("/{email}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String email, @RequestBody UserUpdateDTO userUpdateDTO) {
        return new ResponseEntity<>(userService.updateUser(email, userUpdateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable String email) {
        return new ResponseEntity<>(userService.deleteUser(email), HttpStatus. OK);
    }
}
