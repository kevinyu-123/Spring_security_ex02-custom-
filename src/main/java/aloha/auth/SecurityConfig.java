package aloha.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration            // 이 클래스를 스프링 설정 빈으로 등록
@EnableWebSecurity         // 이 클래스에 스프링 시큐리티 기능 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

   @Override
   protected void configure(HttpSecurity http) throws Exception {
      // 인가 
      // authorizeRequests()          : URL 요청에 대한  접근권한 설정
      // permitAll()               : 모두에게 접근 허용
      // hasRole(), hasAnyRoes()      : 특정권한을 가진 사용자만 허용
      http.authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/css/**", "/js/**", "/img/**").permitAll()
            .antMatchers("/guest/**").permitAll()
            .antMatchers("/member/**").hasAnyRole("USER", "ADMIN")
            .antMatchers("/admin/**").hasRole("ADMIN")
         .anyRequest().authenticated();
      
      // 로그인 설정
      // - default 로그인 화면 URL    	 : /login
      // - custom  로그인 화면 URL	 	 : /auth/loginForm
      // - custom  로그인 처리 URL		 : /auth
      // - custom  로그인 에러 화면 URL    : /auth/loginError
      
      http.formLogin()
      	  .loginPage("/auth/loginForm")
      	  .loginProcessingUrl("/auth/login")
      	  .failureUrl("/auth/loginError")
      	  .usernameParameter("username")
      	  .passwordParameter("password")
          .permitAll();
      
      // 로그아웃 설정
      // - default 로그아웃 화면 URL 	 	: /logout
      // - custom 로그아웃 화면 URL		 	: /auth/logout
      // - default 로그아웃 성공 후 이동 URL 	:/[로그인경로]
      // - custom 로그아웃 경로  			 	: /
      // - invaliedateHttpSession(true) : 로그아웃시 , 세션이 유효하지 않도록 지정 (세션에 등록된 로그인 정보를 삭제하기 위함)
      
      http.logout()
      	  .logoutUrl("/auth/logout")
      	  .logoutSuccessUrl("/")
      	  .invalidateHttpSession(true)
          .permitAll();
      	  
      
      //접근 거부 처리
      http.exceptionHandling()
      	  .accessDeniedPage("/auth/accessError");
      
   }
   
   
   @Autowired
   public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
      // inMemory 방식으로 인증 사용자 등록
      // ID : user   /  pw : 1234   / 권한 : USER
      // ID :   admin  /  pw : 1234   / 권한 : ADMIN
      auth.inMemoryAuthentication()
         .withUser("user").password(passwordEncoder().encode("1234")).roles("USER")
         .and()
         .withUser("admin").password(passwordEncoder().encode("1234")).roles("ADMIN");
      
   }
   
   //패스워드 암호화
   @Bean
   public BCryptPasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }
   

}


