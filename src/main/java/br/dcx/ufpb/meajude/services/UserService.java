package br.dcx.ufpb.meajude.services;

import br.dcx.ufpb.meajude.dtos.user.UserDTO;
import br.dcx.ufpb.meajude.dtos.user.UserRegistrationDTO;
import br.dcx.ufpb.meajude.dtos.user.UserUpdateDTO;
import br.dcx.ufpb.meajude.entities.User;
import br.dcx.ufpb.meajude.entities.enums.Role;
import br.dcx.ufpb.meajude.exceptions.UnauthorizedException;
import br.dcx.ufpb.meajude.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Optional<User> optionalUser = userRepository.findActiveUserByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            userUpdateDTO.update(user);
            userRepository.save(user);
            UserDTO updatedUserDTO = UserDTO.from(user);
            userRepository.save(user);
            return updatedUserDTO;
        }
        return null;
    }

    public UserDTO deleteUser(String email) {
        Optional<User> optionalUser = userRepository.findActiveUserByEmail(email);

        if (optionalUser.isPresent()) {
            if (optionalUser.get().getEmail().equals(email)) {
                System.out.println("if");
                User user = optionalUser.get();
                user.setDeleted(true);
                userRepository.delete(user);
                return UserDTO.from(user);
            }
        }
        return null;
    }
}
