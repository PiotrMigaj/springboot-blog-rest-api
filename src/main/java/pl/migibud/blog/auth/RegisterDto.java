package pl.migibud.blog.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
class RegisterDto {
    @NotEmpty
    private String name;
    @NotEmpty
    private String userName;
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
}
