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
                switch (updateInformation) {
                    case "ONG" -> {
                        user.setUserType(UserType.ONG);
                        return user;
                    }
                    case "PERSON" -> {
                        user.setUserType(UserType.PERSON);
                        return user;
                    }
                    case "CHURCH" -> {
                        user.setUserType(UserType.CHURCH);
                        return user;
                    }
                    case "SOCIETY" -> {
                        user.setUserType(UserType.SOCIETY);
                        return user;
                    }
                    case "ASSOCIATION" -> {
                        user.setUserType(UserType.ASSOCIATION);
                        return user;
                    }
                    case "STATE_GOVERNMENT" -> {
                        user.setUserType(UserType.STATE_GOVERNMENT);
                        return user;
                    }
                    case "FEDERAL_GOVERNMENT" -> {
                        user.setUserType(UserType.FEDERAL_GOVERNMENT);
                        return user;
                    }
                    case "MUNICIPAL_GOVERNMENT" -> {
                        user.setUserType(UserType.MUNICIPAL_GOVERNMENT);
                        return user;
                    }
                }
            case "phone":
                user.setPhone(this.updateInformation);
                return user;
            default:
                throw new InvalidFieldException("Invalid Field", "Verify field.");
        }
    }
}
