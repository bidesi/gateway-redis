package in.sabnar.gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

import javax.annotation.Resource;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	RestAuthenticationEntryPoint restAuthenticationEntryPoint;

	@Resource(name = "userService")
	private UserDetailsService userDetailsService;

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http
//				.csrf().disable()
//				.exceptionHandling()
//				.authenticationEntryPoint(restAuthenticationEntryPoint)
//				.and()
//				.formLogin()
//				.successHandler(new SessionAuthenticationSuccessHandler())
//				.failureHandler(new SimpleUrlAuthenticationFailureHandler())
//				.and()
//				.logout()
//				.defaultLogoutSuccessHandlerFor(new HttpStatusReturningLogoutSuccessHandler(),
//						new AntPathRequestMatcher("/logout"))
//				.and()
//				.authorizeRequests()
//				.antMatchers("/login").permitAll()
//				.antMatchers("/h2/**").permitAll()
//				.and()
//				.authorizeRequests().antMatchers("/resource/**", "/api/**").hasAnyRole("ADMIN", "USER")
//				.and()
//				.requestCache()
//				.requestCache(new NullRequestCache());

		http
				.authorizeRequests()
				.anyRequest().authenticated()
				.and()
				.requestCache()
				.requestCache(new NullRequestCache())
				.and()
				.httpBasic();
	}

	@Bean
	public HttpSessionIdResolver httpSessionIdResolver() {
		return new HeaderHttpSessionIdResolver("X-Auth-Token");
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// Allow eureka client to be accessed without authentication
		web.ignoring().antMatchers("/*/")//
				.antMatchers("/eureka/**")//
				.antMatchers(HttpMethod.OPTIONS, "/**"); // Request type options should be allowed.
	}
}
