package br.dcx.ufpb.meajude.dtos.user;

import br.dcx.ufpb.meajude.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationDTO{
    private String email;
    private String password;

    public static AuthenticationDTO from(User user) {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO();
        authenticationDTO.setEmail(user.getEmail());
        authenticationDTO.setPassword(user.getPassword());
        return authenticationDTO;
    }
}
