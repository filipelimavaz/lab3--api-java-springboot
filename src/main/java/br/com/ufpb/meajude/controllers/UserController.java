package br.com.ufpb.meajude.controllers;

import br.com.ufpb.meajude.dtos.UserDTO;
import br.com.ufpb.meajude.dtos.UserRegistrationDTO;
import br.com.ufpb.meajude.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/sign-up")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        return new ResponseEntity<>(userService.registerUser(userRegistrationDTO), HttpStatus.CREATED);
    }
}
