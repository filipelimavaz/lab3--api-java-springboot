package br.dcx.ufpb.meajude.controllers;

import br.dcx.ufpb.meajude.services.UserService;
import br.dcx.ufpb.meajude.dtos.user.UserDTO;
import br.dcx.ufpb.meajude.dtos.user.UserUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{email}")
    public ResponseEntity<UserDTO> returnUser(@PathVariable String email) {
        return new ResponseEntity<>(userService.returnUser(email), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> returnUserList(){
        return new ResponseEntity<List<UserDTO>>(userService.returnUserList(), HttpStatus.OK);
    }

    @PatchMapping("/{email}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String email, @RequestBody UserUpdateDTO userUpdateDTO, @RequestHeader("Authorization") String header) {
        return new ResponseEntity<>(userService.updateUser(email, userUpdateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable String email, @RequestHeader("Authorization") String header) {
        return new ResponseEntity<>(userService.deleteUser(email), HttpStatus. OK);
    }
}
