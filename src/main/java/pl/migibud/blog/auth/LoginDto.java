package pl.migibud.blog.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    @NotEmpty
    private String userNameOrEmail;
    @NotEmpty
    private String password;
}
