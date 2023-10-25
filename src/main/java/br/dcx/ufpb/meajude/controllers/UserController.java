package br.dcx.ufpb.meajude.controllers;

import br.dcx.ufpb.meajude.services.UserService;
import br.dcx.ufpb.meajude.dtos.user.UserDTO;
import br.dcx.ufpb.meajude.dtos.user.UserUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    @Operation(summary = "User by email", description = "Return user by email", tags = { "Users" }, responses = {
            @ApiResponse(responseCode = "200", description = "User returned"),
            @ApiResponse(responseCode = "404", description = "User not found")

    })
    public ResponseEntity<UserDTO> returnUser(@PathVariable String email) {
        return new ResponseEntity<>(userService.returnUser(email), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "User list", description = "Returning user list", tags = { "Users" }, responses = {
            @ApiResponse(responseCode = "200", description = "User list returned"),
            @ApiResponse(responseCode = "404", description = "No users were found")
    })
    public ResponseEntity<List<UserDTO>> returnUserList(){
        return new ResponseEntity<List<UserDTO>>(userService.returnUserList(), HttpStatus.OK);
    }

    @PatchMapping("/{email}")
    @Operation(summary = "Update user", description = "Updating user", security = @SecurityRequirement(name = "bearerAuth"), tags = { "Users" }, responses = {
            @ApiResponse(responseCode = "200", description = "Information user updated"),
            @ApiResponse(responseCode = "403", description = "User must be authenticated"),
            @ApiResponse(responseCode = "400", description = "Incorrect parameters")

    })
    public ResponseEntity<UserDTO> updateUser(@PathVariable String email, @RequestBody UserUpdateDTO userUpdateDTO, @Parameter(description = "Bearer Token Authorization", required = true, hidden = true, schema = @Schema(implementation = String.class)) @RequestHeader("Authorization") String header) {
        return new ResponseEntity<>(userService.updateUser(email, userUpdateDTO), HttpStatus.OK);
    }

    @PatchMapping("/delete/{email}")
    @Operation(summary = "Delete user", description = "Deleting user", security = @SecurityRequirement(name = "bearerAuth"), tags = { "Users" }, responses = {
            @ApiResponse(responseCode = "200", description = "The user was deleted"),
            @ApiResponse(responseCode = "403", description = "User must be authenticated"),
            @ApiResponse(responseCode = "400", description = "Incorrect parameters")
    })
    public ResponseEntity<UserDTO> deleteUser(@PathVariable String email, @Parameter(description = "Bearer Token Authorization", required = true, hidden = true, schema = @Schema(implementation = String.class)) @RequestHeader("Authorization") String header) {
        return new ResponseEntity<>(userService.deleteUser(email), HttpStatus. OK);
    }
}
