package br.com.ufpb.meajude.services;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.ufpb.meajude.dtos.UserRegistrationDTO;
import br.com.ufpb.meajude.entities.User;
import br.com.ufpb.meajude.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Lazy
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CPFValidator cpfValidator;
    @Autowired
    private CNPJValidator cnpjValidator;

    public User registerUser(UserRegistrationDTO userRegistrationDTO) {
        if(userRegistrationDTO.getUserType().equals("Person")) {
            cpfValidator.assertValid("OK");
        } else {
            cnpjValidator.assertValid("OK");
        }
        return null;
    }
}
