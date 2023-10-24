package br.dcx.ufpb.meajude.controllers;

import br.dcx.ufpb.meajude.dtos.user.AuthenticationDTO;
import br.dcx.ufpb.meajude.dtos.user.LoginResponseDTO;
import br.dcx.ufpb.meajude.dtos.user.UserRegistrationDTO;
import br.dcx.ufpb.meajude.repositories.UserRepository;
import br.dcx.ufpb.meajude.services.AuthorizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private AuthorizationService authorizationService;

    @PostMapping("/login")
    @Operation(summary = "Authenticates the properly user", description = "Email and password", tags = { "Authentication"}, responses = {
            @ApiResponse(responseCode = "200", description = "User authenticated"),
            @ApiResponse(responseCode = "401", description = "Invalid login")
    })
    public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody AuthenticationDTO authenticationDTO) {
        return new ResponseEntity<>(authorizationService.loginUser(authenticationDTO), HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    @Operation(summary = "User sign-up", description = "Sign up a new user", tags = { "Authentication" }, responses = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request. Please, check information")
    })
    public ResponseEntity<UserRegistrationDTO> registerUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        return new ResponseEntity<>(authorizationService.registerUser(userRegistrationDTO), HttpStatus.CREATED);
    }
}
