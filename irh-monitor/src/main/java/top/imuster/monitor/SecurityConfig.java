package top.imuster.monitor;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * The class Security config.
 *
 * @author paascloud.net @gmail.com
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable()
				.and()
				.formLogin()
				.loginPage("/login.html")
				.loginProcessingUrl("/login")
				.and()
				.logout().logoutUrl("/logout")
				.and()
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/**/**", "/applications/**", "/api/applications/**", "/login.html", "/**/*.css", "/img/**", "/third-party/**")
				.permitAll()
				.anyRequest().authenticated();
	}
}