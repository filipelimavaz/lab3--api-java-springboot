package br.dcx.ufpb.meajude.services;

import br.dcx.ufpb.meajude.dtos.user.AuthenticationDTO;
import br.dcx.ufpb.meajude.dtos.user.LoginResponseDTO;
import br.dcx.ufpb.meajude.entities.User;
import br.dcx.ufpb.meajude.dtos.user.UserRegistrationDTO;
import br.dcx.ufpb.meajude.exceptions.InvalidRequestException;
import br.dcx.ufpb.meajude.exceptions.UnauthorizedException;
import br.dcx.ufpb.meajude.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    private User user;

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userRepository.findByEmail(email);
    }

    public UserRegistrationDTO registerUser(UserRegistrationDTO userRegistrationDTO) {
        UserDetails optionalUserEmailVerify = userRepository.findByEmail(userRegistrationDTO.getEmail());
        if(optionalUserEmailVerify == null) {
            User user = new User();
            user.setEmail(userRegistrationDTO.getEmail());
            user.setUsername(userRegistrationDTO.getUsername());
            user.setPassword(new BCryptPasswordEncoder().encode(userRegistrationDTO.getPassword()));
            user.setIdentityDocument(userRegistrationDTO.getIdentityDocument());
            user.setUserType(userRegistrationDTO.getUserType());
            user.setRole(userRegistrationDTO.getRole());
            user.setPhone(userRegistrationDTO.getPhone());
            user.setDeleted(false);
            userRepository.save(user);
            return UserRegistrationDTO.from(user);
        }
        throw new InvalidRequestException("User can't be registered",
                "The request cannot be fulfilled. There is a registered user with this email");
    }

    public LoginResponseDTO loginUser(AuthenticationDTO authenticationDTO) {
        UsernamePasswordAuthenticationToken usernamePassword =
                new UsernamePasswordAuthenticationToken(authenticationDTO.getEmail(), authenticationDTO.getPassword());

        try {
            Authentication auth = this.authenticationManager.authenticate(usernamePassword);

            String token = tokenService.generateToken((User) auth.getPrincipal());

            Optional<User> optionalUser = userRepository.findActiveUserByEmail(authenticationDTO.getEmail());
            this.user = optionalUser.get();

            return new LoginResponseDTO(token);
        } catch (UnauthorizedException e) {
            throw new UnauthorizedException("Incorrect email or password", "Please check if the email and password are correct.");
        }
    }


    public User getLoggedUser() {
        return user;
    }

    public boolean isUserLoggedIn() {
        return user != null;
    }
}