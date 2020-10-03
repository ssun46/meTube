package com.dev.metube.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dev.metube.model.LoginUserDetails;
import com.dev.metube.model.User;
import com.dev.metube.service.UserLoginDetailsService;
import com.dev.metube.service.UserService;

@Controller
public class MainController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping("/")
	public String contentList() {
		return "contents/contentsList";
	}
	
	@RequestMapping("/chennel")
	public String chennel(Model model) {
		LoginUserDetails userDetails = (LoginUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.getUserByUsername(userDetails.getUsername());
		model.addAttribute("user", user);
		return "channel/channel_menu";
	}
	
	@RequestMapping("/login")
	public String signinByGet() {
		System.out.println("Login controller Invorked");
		return "login";
	}
	
	@RequestMapping("/login/processing")
	public String signinByPost(@ModelAttribute LoginUserDetails user) {
		System.out.println("Login Processing controller Invorked");
		UserLoginDetailsService userDetails = (UserLoginDetailsService) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println(userDetails);
		return "login";
	}
	
	@GetMapping("/signup")
	public String signupByGet() {
		return "signup";
	}
	
	@PostMapping("/signup")
	public String signupByPost(User user) {
		userService.createUser(user);
		return "redirect:/";
	}
	
	@PostMapping("/checkUserExist")
	public @ResponseBody boolean getUserCount(@RequestParam("username") String username) {
		return userService.checkUserExist(username);
	}
}
