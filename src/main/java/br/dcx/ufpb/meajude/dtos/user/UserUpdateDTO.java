package br.dcx.ufpb.meajude.dtos.user;

import br.dcx.ufpb.meajude.entities.User;
import br.dcx.ufpb.meajude.entities.enums.CampaignStatus;
import br.dcx.ufpb.meajude.entities.enums.Role;
import br.dcx.ufpb.meajude.entities.enums.UserType;
import br.dcx.ufpb.meajude.exceptions.InvalidFieldException;
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
        String field = this.updateField;

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
            case "phone":
                user.setPhone(this.updateInformation);
                return user;
            default:
                throw new InvalidFieldException("Invalid Field", "Verify field.");
        }
    }
}
