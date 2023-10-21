package br.dcx.ufpb.meajude.controllers;

import br.dcx.ufpb.meajude.dtos.user.AuthenticationDTO;
import br.dcx.ufpb.meajude.dtos.user.LoginResponseDTO;
import br.dcx.ufpb.meajude.dtos.user.UserRegistrationDTO;
import br.dcx.ufpb.meajude.repositories.UserRepository;
import br.dcx.ufpb.meajude.services.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
public class AuthenticationController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private AuthorizationService authorizationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody AuthenticationDTO authenticationDTO) {
        return new ResponseEntity<>(authorizationService.loginUser(authenticationDTO), HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserRegistrationDTO> registerUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        return new ResponseEntity<>(authorizationService.registerUser(userRegistrationDTO), HttpStatus.CREATED);
    }
}
