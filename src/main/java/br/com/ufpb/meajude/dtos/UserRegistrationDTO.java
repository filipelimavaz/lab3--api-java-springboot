package br.com.ufpb.meajude.dtos;

import br.com.ufpb.meajude.entities.User;
import br.com.ufpb.meajude.entities.enums.UserType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRegistrationDTO {

    private String email;
    private String password;
    private String identityDocument;
    private UserType userType;
    private String phone;

    public static UserRegistrationDTO from(User user) {
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
        userRegistrationDTO.setEmail(user.getEmail());
        userRegistrationDTO.setPassword(user.getPassword());
        userRegistrationDTO.setIdentityDocument(user.getIdentityDocument());
        userRegistrationDTO.setUserType(user.getUserType());
        userRegistrationDTO.setPhone(user.getPhone());
        return userRegistrationDTO;
    }
}
