package aloha.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {
	
	
	@RequestMapping("/")
	public @ResponseBody String main() throws Exception {
	
		return "ALOHA CAMPUS";
	}
	
	
	@RequestMapping("/guest/welcome")
	public String guestWelcome() {
		return "guest/welcome";
	}
	
	@RequestMapping("/member/welcome")
	public String memeberWelcome() {
		return "member/welcome";
	}
	
	@RequestMapping("/admin/welcome")
	public String adminWelcome() {
		return "admin/welcome";
	}
	//custom 로그인 페이지
	@RequestMapping("/auth/loginForm")
	public String loginForm() {
		return "auth/loginForm";
	}
	
	//custom 로그인 에러 페이지
	@RequestMapping("/auth/loginError")
	public String loginError() {
		return "auth/loginError";
	}

	//custom 접근 거부 페이지
	@RequestMapping("/auth/accessError")
	public String accessError(Model model) {
		model.addAttribute("msg","접근이 제한되었습니다.");
		return "auth/accessError";
	}
	

}
