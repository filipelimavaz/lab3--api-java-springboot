package br.com.ufpb.meajude.services;

import br.com.ufpb.meajude.dtos.UserDTO;
import br.com.ufpb.meajude.dtos.UserRegistrationDTO;
import br.com.ufpb.meajude.entities.User;
import br.com.ufpb.meajude.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
