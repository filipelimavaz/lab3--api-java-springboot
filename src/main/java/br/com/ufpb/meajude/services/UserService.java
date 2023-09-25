package br.com.ufpb.meajude.services;

import br.com.ufpb.meajude.dtos.UserDTO;
import br.com.ufpb.meajude.dtos.UserRegistrationDTO;
import br.com.ufpb.meajude.entities.User;
import br.com.ufpb.meajude.repositories.UserRepository;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Lazy
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CPFValidator cpfValidator;
    @Autowired
    private CNPJValidator cnpjValidator;

    public boolean isIdentityValid(String document, String number) {
        if(document.equals("Person")) {
            return cpfValidator.isValid(number, null);
        } else {
            return cnpjValidator.isValid(number, null);
        }
    }

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
            user.setIdentityDocument(userRegistrationDTO.getIdentityDocument());
            user.setUserType(userRegistrationDTO.getUserType());
            user.setPhone(userRegistrationDTO.getPhone());
            return UserDTO.from(user);
        }

        return null;
    }
}
