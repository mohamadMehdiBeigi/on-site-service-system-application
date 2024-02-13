package ir.example.finalPart03.config;

import ir.example.finalPart03.service.AdminService;
import ir.example.finalPart03.service.CustomerService;
import ir.example.finalPart03.service.SpecialistService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration {

    private final AdminService adminService;

    private final SpecialistService specialistService;

    private final CustomerService customerService;

    private final BCryptPasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(a -> a.anyRequest().permitAll())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService((email) -> adminService.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException(String.format("this %s is notFound!", email))))
                .passwordEncoder(passwordEncoder);

        auth.userDetailsService((email) -> customerService.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException(String.format("this %s is notFound!", email))))
                .passwordEncoder(passwordEncoder);

        auth.userDetailsService((email) -> specialistService.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException(String.format("this %s is notFound!", email))))
                .passwordEncoder(passwordEncoder);
    }
}
