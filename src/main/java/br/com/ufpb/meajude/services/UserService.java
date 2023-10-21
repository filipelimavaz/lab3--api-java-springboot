package br.com.ufpb.meajude.services;

import br.com.ufpb.meajude.dtos.user.UserDTO;
import br.com.ufpb.meajude.dtos.user.UserUpdateDTO;
import br.com.ufpb.meajude.entities.User;
import br.com.ufpb.meajude.exceptions.InvalidRequestException;
import br.com.ufpb.meajude.exceptions.NotFoundException;
import br.com.ufpb.meajude.exceptions.UnauthorizedException;
import br.com.ufpb.meajude.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorizationService authorizationService;

    public UserDTO returnUser(String email) {
        Optional<User> optionalUser = userRepository.findActiveUserByEmail(email);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            return UserDTO.from(user);
        }
        return null;
    }

    public List<UserDTO> returnUserList(){
        List<User> userList = userRepository.findAllActiveUsers();
        List<UserDTO> userDTOList = new ArrayList<>();

        if(!userList.isEmpty()) {
            for(User user: userList){
                UserDTO userDTO = UserDTO.from(user);
                userDTOList.add(userDTO);
            }
            return userDTOList;
        }
        return null;
    }

    public UserDTO updateUser(String email, UserUpdateDTO userUpdateDTO) {
        if(!authorizationService.isUserLoggedIn()) {
            throw new InvalidRequestException("Invalid Resquest",
                    "You must be logged in to do an user update");
        }

        Optional<User> optionalUser = userRepository.findActiveUserByEmail(email);
        String userEmail = authorizationService.getLoggedUser().getEmail();

        if (optionalUser.isPresent()) {
            if (userEmail.equals(email)) {
                User user = optionalUser.get();
                userUpdateDTO.update(user);
                userRepository.save(user);
                return UserDTO.from(user);
            } else {
                throw new UnauthorizedException("User does not have permission",
                        "The required operation cannot be performed by this user");
            }
        }
        throw new NotFoundException("User was not found",
                "Please verify if the entered email is correct or if it is registered on the platform.");
    }

    public UserDTO deleteUser(String email) {
        Optional<User> optionalUser = userRepository.findActiveUserByEmail(email);
        String userEmail = authorizationService.getLoggedUser().getEmail();
        UserDetails userDetails = userRepository.findByEmail(userEmail);

        if (optionalUser.isPresent()) {
            if (userEmail.equals(email) || userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                System.out.println("if");
                User user = optionalUser.get();
                user.setDeleted(true);
                userRepository.delete(user);
                return UserDTO.from(user);
            } else {
                throw new UnauthorizedException("User does not have permission",
                        "The required operation cannot be performed by this user");
            }
        }
        throw new NotFoundException("User was not found",
                "Please verify if the entered email is correct or if it is registered on the platform.");
    }
}
