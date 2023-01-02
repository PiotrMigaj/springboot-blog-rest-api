package pl.migibud.blog.post;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@TestConfiguration
class SpringSecurityTestConfiguration {

    @Bean
    @Primary
    UserDetailsService userDetailsService(){
        UserDetails admin = User.builder()
                .username("admin")
                .password("$2a$10$IPK3nbDh0cwWKqHZCaPSW.g13eonoXklePBMVelSBLNLTs3p1blGu")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(admin);
    }

}
