package com.edmond.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.edmond.entity.User;
import com.edmond.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	@Autowired
	private UserService uService;

	@GetMapping("/")
	public String index() {
		return "index";
	}


	@GetMapping("/signin")
	public String login() {
		return "login";
	}

	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute User user, HttpSession session) {
		boolean f = uService.existEmailCheck(user.getEmail());
		if (f) {
			session.setAttribute("msg", "email already exist");
		} else {
			User suser = uService.saveUser(user);
			if (suser != null) {
				session.setAttribute("msg", "successfully saved");
			} else {
				session.setAttribute("msg", "something wrong");
			}
		}
		return "redirect:/user/register";
	}

}
