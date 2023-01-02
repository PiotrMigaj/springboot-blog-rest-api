package pl.migibud.blog.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    ResponseEntity<JwtAuthResponse> login(@Valid @RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse(token);
        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping("/register")
    ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto){
        String result = authService.register(registerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
