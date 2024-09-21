package com.edmond.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.edmond.entity.User;
import com.edmond.repository.UserRepository;

import jakarta.servlet.http.HttpSession;


@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository uRepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public User saveUser(User user) {
		user.setRole("ROLE_USER");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return uRepo.save(user);
	}

	@Override
	public boolean existEmailCheck(String email) {
		
		return uRepo.existsByEmail(email);
	}
	
	public void removeSessionMessage() {
		HttpSession session=((ServletRequestAttributes)(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
		session.removeAttribute("msg");
	}
	

}
