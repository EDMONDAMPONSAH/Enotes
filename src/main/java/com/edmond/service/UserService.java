package com.edmond.service;

import com.edmond.entity.User;

public interface UserService {
	
	public User saveUser(User user);
	
	public boolean existEmailCheck(String email);
	
	

}
