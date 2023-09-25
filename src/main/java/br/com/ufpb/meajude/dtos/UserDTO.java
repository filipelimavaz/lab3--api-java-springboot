package br.com.ufpb.meajude.dtos;

import br.com.ufpb.meajude.entities.Campaign;
import br.com.ufpb.meajude.entities.User;
import br.com.ufpb.meajude.entities.enums.UserType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDTO {

    private String email;
    private String username;
    private UserType userType;
    private String phone;
    private List<Campaign> campaignList;

    public static UserDTO from(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.username = user.getUsername();
        userDTO.email = user.getEmail();
        userDTO.userType = user.getUserType();
        userDTO.phone = user.getPhone();
        userDTO.campaignList = user.getCampaignList();
        return userDTO;
    }
}