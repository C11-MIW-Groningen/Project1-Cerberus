package nl.miwgroningen.c11.cerberus.docentenportaalproject.configuration;

import lombok.RequiredArgsConstructor;
import nl.miwgroningen.c11.cerberus.docentenportaalproject.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration to handle access roles to the app resources
 *
 * @author Marianne Kooistra, Mohammed Almameri, Joost Schreuder 15/06/2023
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserService userService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests(authorize -> authorize
                        .antMatchers("/css/**", "/webjars/**",
                                "/images/**", "/js/**", "/photos/**").permitAll()
                        .antMatchers("/", "/home", "/programme/view/**").permitAll()

                        //TestAttempt Permissions
                        .antMatchers("/test/**/attempt/**/add").hasAnyAuthority("ADMIN", "STUDENT")
                        .antMatchers("/testAttempt/edit/student/**").hasAnyAuthority("ADMIN", "STUDENT")
                        .antMatchers("/testAttempt/edit/teacher/**").hasAnyAuthority("ADMIN", "TEACHER")
                        .antMatchers("/testAttempt/**")
                            .hasAnyAuthority("ADMIN", "TEACHER", "STUDENT")

                        .antMatchers("/test/**", "/assignment/**").hasAnyAuthority("ADMIN", "TEACHER")
                        .antMatchers("/teacher/**", "/seed", "home/delete/**",
                                "/**/edit/**", "/**/new", "/**/delete/**", "/**/add/**").hasAuthority("ADMIN")
                        .antMatchers(HttpMethod.POST, "/test/**").hasAnyAuthority("ADMIN", "TEACHER")
                        .antMatchers("/user/resetPassword/**").hasAuthority("ADMIN")
                        .antMatchers("/user/changePassword").authenticated()
                        .antMatchers(HttpMethod.POST, "/**").hasAuthority("ADMIN")
                        .antMatchers("/cohort/**").hasAnyAuthority("ADMIN", "TEACHER")
                        .anyRequest().hasAnyAuthority("ADMIN", "TEACHER", "STUDENT")
                )
                .formLogin().loginPage("/login").permitAll().and().logout().logoutSuccessUrl("/");
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}
