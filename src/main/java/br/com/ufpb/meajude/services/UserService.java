package br.com.ufpb.meajude.services;

import br.com.ufpb.meajude.dtos.user.UserDTO;
import br.com.ufpb.meajude.dtos.user.UserRegistrationDTO;
import br.com.ufpb.meajude.dtos.user.UserUpdateDTO;
import br.com.ufpb.meajude.entities.User;
import br.com.ufpb.meajude.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDTO registerUser(UserRegistrationDTO userRegistrationDTO) {
        Optional<User> optionalUser = userRepository.findActiveUserByEmail(userRegistrationDTO.getEmail());
        if(optionalUser.isEmpty()) {
            User user = new User();
            user.setUsername(userRegistrationDTO.getUsername());
            user.setEmail(userRegistrationDTO.getEmail());
            user.setPassword(userRegistrationDTO.getPassword());
            user.setUserType(userRegistrationDTO.getUserType());
            user.setIdentityDocument(userRegistrationDTO.getIdentityDocument());
            user.setPhone(userRegistrationDTO.getPhone());
            user.setRole(userRegistrationDTO.getRole());
            user.setDeleted(false);
            userRepository.save(user);

            return UserDTO.from(user);
        }
        return null;
    }

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

        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setDeleted(true);
            userRepository.save(user);
            return UserDTO.from(user);
        }
        return UserDTO.from(optionalUser.get());
    }
}
