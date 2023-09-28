package br.com.ufpb.meajude.dtos.user;

import br.com.ufpb.meajude.dtos.user.UserDTO;
import br.com.ufpb.meajude.entities.User;
import br.com.ufpb.meajude.entities.enums.Role;
import br.com.ufpb.meajude.entities.enums.UserType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserUpdateDTO {

    private String updateField;
    private String updateInformation;

    public static UserDTO from(User user) {
        UserDTO userDTO = UserDTO.from(user);
        return userDTO;
    }

    public User update(User user) {
        String field = this.updateField.toLowerCase();

        switch (field) {
            case "email":
                user.setEmail(this.updateInformation);
                return user;
            case "username":
                user.setUsername(this.updateInformation);
                return user;
            case "password" :
                user.setPassword(this.updateInformation);
                return user;
            case "identityDocument":
                user.setIdentityDocument(this.updateInformation);
                return user;
            case "userType":
                user.setUserType(UserType.valueOf(this.updateInformation));
                return user;
            case "role":
                user.setRole(Role.valueOf(this.updateInformation));
                return user;
            case "phone":
                user.setPhone(this.updateInformation);
                return user;
            default:
                return null;
        }
    }
}
