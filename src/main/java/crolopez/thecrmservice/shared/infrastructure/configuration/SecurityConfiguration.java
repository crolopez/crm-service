package crolopez.thecrmservice.shared.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.web.SecurityFilterChain;

import static crolopez.thecrmservice.shared.domain.entities.dto.UserDto.RoleEnum.ADMIN;
import static crolopez.thecrmservice.shared.domain.entities.dto.UserDto.RoleEnum.USER;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final String USER_ROLE = USER.toString();
    private final String ADMIN_ROLE = ADMIN.toString();

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().xssProtection()
                .block(true)
                .xssProtectionEnabled(true)
                .xssProtectionEnabled(true)
                .and()
                .and()
                .authorizeRequests()
                .antMatchers("/v1/customer/**").hasAnyRole(USER_ROLE, ADMIN_ROLE)
                .antMatchers("/v1/user/**").hasRole(ADMIN_ROLE)
                .antMatchers("/v1/login/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer()
                .opaqueToken();
    }

}
