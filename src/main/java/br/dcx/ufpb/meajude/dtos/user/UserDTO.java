package br.dcx.ufpb.meajude.dtos.user;

import br.dcx.ufpb.meajude.entities.User;
import br.dcx.ufpb.meajude.entities.enums.Role;
import br.dcx.ufpb.meajude.entities.enums.UserType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
    private String email;
    private String username;
    private UserType userType;
    private String phone;
    private Role role;

    public static UserDTO from(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setUserType(user.getUserType());
        userDTO.setPhone(user.getPhone());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

}
