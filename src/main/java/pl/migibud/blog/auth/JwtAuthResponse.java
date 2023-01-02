package pl.migibud.blog.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
class JwtAuthResponse {
    private String accessToken;
    private String tokenType="Bearer";

    JwtAuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
