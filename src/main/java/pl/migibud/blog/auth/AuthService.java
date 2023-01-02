package pl.migibud.blog.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.migibud.blog.user.*;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    String login(LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUserNameOrEmail(),
                        loginDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }

    String register(RegisterDto registerDto){

        validateRegistrationData(registerDto);
        User user = new User(
                registerDto.getName(),
                registerDto.getUserName(),
                registerDto.getEmail(),
                passwordEncoder.encode(registerDto.getPassword())
        );

        Role roleUser = roleRepository.findByName("ROLE_USER")
                .get();
        Set<Role> roles = new HashSet<>();
        roles.add(roleUser);
        user.setRoles(roles);

        userRepository.save(user);

        return "User registered successfully.";
    }

    private void validateRegistrationData(RegisterDto registerDto) {
        if(userRepository.existsByUserName(registerDto.getUserName())){
            throw new UserException(UserError.USER_WITH_USERNAME_ALREADY_EXISTS,"User with such a username already exists.");
        }
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new UserException(UserError.USER_WITH_EMAIL_ALREADY_EXISTS,"User with such an email already exists.");
        }
    }

}
