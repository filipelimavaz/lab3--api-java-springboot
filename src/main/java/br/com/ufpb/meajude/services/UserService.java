package br.com.ufpb.meajude.services;

import br.com.ufpb.meajude.dtos.UserDTO;
import br.com.ufpb.meajude.dtos.UserRegistrationDTO;
import br.com.ufpb.meajude.dtos.UserUpdateDTO;
import br.com.ufpb.meajude.entities.User;
import br.com.ufpb.meajude.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUser(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new RuntimeException("Usuário não encontrado");
        }
    }

    public UserDTO registerUser(UserRegistrationDTO userRegistrationDTO) {
        Optional<User> optionalUser = userRepository.findByEmail(userRegistrationDTO.getEmail());
        if(optionalUser.isEmpty()) {
            User user = new User();
            user.setUsername(userRegistrationDTO.getUsername());
            user.setEmail(userRegistrationDTO.getEmail());
            user.setPassword(userRegistrationDTO.getPassword());
            user.setUserType(userRegistrationDTO.getUserType());
            user.setIdentityDocument(userRegistrationDTO.getIdentityDocument());
            user.setPhone(userRegistrationDTO.getPhone());
            user.setRole(userRegistrationDTO.getRole());
            userRepository.save(user);

            return UserDTO.from(user);
        }
        return null;
    }

    public UserDTO returnUser(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            return UserDTO.from(user);
        }
        return null;
    }

    public List<UserDTO> returnUserList(){
        List<User> userList = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();

        for(User user: userList){
            UserDTO userDTO = UserDTO.from(user);
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }

    public UserDTO updateUser(String email, UserUpdateDTO userUpdateDTO) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

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
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isPresent()) {
            User user = optionalUser.get();

            userRepository.delete(user);
            return UserDTO.from(user);
        }
        return UserDTO.from(optionalUser.get());
    }
}
