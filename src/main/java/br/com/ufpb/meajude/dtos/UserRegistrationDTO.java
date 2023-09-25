package br.com.ufpb.meajude.dtos;

import br.com.ufpb.meajude.entities.User;
import br.com.ufpb.meajude.entities.enums.UserType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRegistrationDTO {

    private String username;
    private String email;
    private String password;
    private UserType userType;
    private String identityDocument;
    private String phone;

    public static UserRegistrationDTO from(User user) {
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
        userRegistrationDTO.setUsername(user.getUsername());
        userRegistrationDTO.setEmail(user.getEmail());
        userRegistrationDTO.setPassword(user.getPassword());
        userRegistrationDTO.setUserType(user.getUserType());
        userRegistrationDTO.setIdentityDocument(user.getIdentityDocument());
        userRegistrationDTO.setPhone(user.getPhone());
        return userRegistrationDTO;
    }
}
