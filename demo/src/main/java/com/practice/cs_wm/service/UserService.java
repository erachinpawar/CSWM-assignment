package com.practice.cs_wm.service;

import com.practice.cs_wm.model.User;

public interface UserService {

	User saveUser( User user);

	User findUserByEmail(String email);

	String getUserName();

}
