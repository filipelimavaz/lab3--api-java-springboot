package br.com.ufpb.meajude.controllers;

import br.com.ufpb.meajude.dtos.user.*;
import br.com.ufpb.meajude.entities.User;
import br.com.ufpb.meajude.repositories.UserRepository;
import br.com.ufpb.meajude.security.TokenService;
import br.com.ufpb.meajude.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{email}")
    public ResponseEntity<UserDTO> returnUser(@PathVariable String email) {
        return new ResponseEntity<>(userService.returnUser(email), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> returnUserList(){
        return new ResponseEntity<List<UserDTO>>(userService.returnUserList(), HttpStatus.OK);
    }

    @PreAuthorize("authentication.email == #email")
    @PatchMapping("/{email}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String email, @RequestBody UserUpdateDTO userUpdateDTO, @RequestHeader("Authorization") String header) {
        return new ResponseEntity<>(userService.updateUser(email, userUpdateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable String email, @RequestHeader("Authorization") String header) {
        return new ResponseEntity<>(userService.deleteUser(email), HttpStatus. OK);
    }
}
