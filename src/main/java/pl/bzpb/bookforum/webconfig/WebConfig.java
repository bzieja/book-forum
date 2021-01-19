package pl.bzpb.bookforum.webconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.bzpb.bookforum.filters.JwtFilter;
import pl.bzpb.bookforum.services.config.MyUserDetailsService;

@EnableWebSecurity
public class WebConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtFilter jwtFilter;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService); //w jaki sposob bedzie autoryziowane
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/api/user").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/user/login").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/user/ratings/*").permitAll()

                    .antMatchers(HttpMethod.DELETE,"/api/book/*").hasRole("ADMIN")
                    .antMatchers(HttpMethod.PUT,"/api/book/*").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST,"/api/book").permitAll()
                    .antMatchers(HttpMethod.GET,"/api/book").permitAll()

                    .antMatchers(HttpMethod.DELETE,"/api/book/rating/*").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST,"/api/book/rating/*").permitAll()
                    .antMatchers(HttpMethod.GET,"/api/book/rating/*").permitAll()

                    .antMatchers("/v3/api-docs/**").permitAll()
                    .antMatchers("/swagger-ui/**").permitAll()
                    .antMatchers("/swagger-ui.html").permitAll()

                .anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        //super.configure(http);
    }

    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
