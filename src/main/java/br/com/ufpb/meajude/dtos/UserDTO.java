package br.com.ufpb.meajude.dtos;

import br.com.ufpb.meajude.entities.User;
import br.com.ufpb.meajude.entities.enums.UserType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {

    private String username;
    private String email;
    private UserType userType;
    private String phone;

    public static UserDTO from(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.username = user.getUsername();
        userDTO.email = user.getEmail();
        userDTO.userType = user.getUserType();
        userDTO.phone = user.getPhone();
        return userDTO;
    }
}
