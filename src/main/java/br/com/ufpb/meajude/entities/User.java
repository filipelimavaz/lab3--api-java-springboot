package br.com.ufpb.meajude.entities;

import br.com.ufpb.meajude.entities.enums.UserType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "This e-mail must be valid")
    @NotBlank(message = "E-mail can't be blank")
    private String email;

    @NotBlank(message = "Username can't be blank")
    private String username;

    @NotBlank(message = "Password can't be blank")
    private String password;

    @NotBlank(message = "The identity document can't be blank")
    private String identityDocument;

    @NotNull(message = "User type can't be blank")
    @Enumerated
    private UserType userType;

    private String phone;

    @ManyToMany(mappedBy = "user")
    private List<Campaign> campaignList = new ArrayList<>();
}
