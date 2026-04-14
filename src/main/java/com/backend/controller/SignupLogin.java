package com.backend.controller;

import java.util.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.backend.RepositoryHolder.RepositoryBundle;
import com.backend.entity.*;

@RestController
@RequestMapping("/auth")
public class SignupLogin {
	
  @Autowired
	private RepositoryBundle Repo;

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody signup signup) {
		String user_id = signup.generateTemplateIdWithUUID();
		String email = signup.getEmailId();
		signup existId = Repo.signupLoginRepo.findByUserId(user_id);
		signup existEmail = null;
		existEmail = Repo.signupLoginRepo.findByEmailId(email);
		if (existId != null) {
			user_id = signup.generateTemplateIdWithUUID();
		}
		if (existEmail != null) {
			return ResponseEntity.badRequest().body("email already exist");
		}
		signup.setUser_id(user_id);
		if (signup.getMobileNumber() == null || !signup.getMobileNumber().matches("\\d{10}")) {
			return ResponseEntity.badRequest().body("Invalid mobile number. It must be 10 digits.");
		}

		String hashedPassword = Repo.passwordEncoder.encode(signup.getPassword());
		signup.setPassword(hashedPassword);
signup saveUser=Repo.signupLoginRepo.save(signup);
// Repo.excelService.saveCustomerToExcel(saveUser);
		return ResponseEntity.ok("Signup Successfully!!");
	}

	
@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody signup login) {

    String email = login.getEmailId();
    String mobileNumber = login.getMobileNumber();
    String password = login.getPassword();

    signup user = null;

    if (email != null && !email.isEmpty()) {
        user = Repo.signupLoginRepo.findByEmailId(email);
    } else if (mobileNumber != null && !mobileNumber.isEmpty()) {
        user = Repo.signupLoginRepo.findByMobileNumber(mobileNumber);
    } else {
        return ResponseEntity.badRequest().body("Email or Mobile Number is required");
    }

    if (user == null) {
        return ResponseEntity.badRequest().body("Invalid credentials");
    }

    // ✅ Manual password check (your way)
    if (!Repo.passwordEncoder.matches(password, user.getPassword())) {
        return ResponseEntity.badRequest().body("Invalid credentials");
    }

    // ✅ IMPORTANT: use unique identifier (NOT fullName)
    UserDetails userDetails = org.springframework.security.core.userdetails.User
            .withUsername(user.getUser_id())  // 🔥 FIXED
            .password(user.getPassword())
            .authorities("user") 
            .build();

    // ✅ Generate token
    String token = Repo.jwtUtils.generateTokenFromUsername(userDetails);

    Map<String, Object> response = new HashMap<>();
    response.put("token", token);
    response.put("message", "Login Successfully");

    return ResponseEntity.ok(response);
}

	// @PostMapping("/login")
	// public ResponseEntity<?> login(@RequestBody signup login) {
	// 	String email = login.getEmailId();
	// 	String mobileNumber = login.getMobileNumber();
	// 	String password = login.getPassword();

	// 	signup user = null;

	// 	if (email != null && !email.isEmpty()) {
	// 		user = Repo.signupLoginRepo.findByEmailId(email);
	// 	} else if (mobileNumber != null && !mobileNumber.isEmpty()) {
	// 		user = Repo.signupLoginRepo.findByMobileNumber(mobileNumber);
	// 	} else {
	// 		return ResponseEntity.badRequest().body("Email or Mobile Number is required");
	// 	}

	// 	if (user == null) {
	// 		return ResponseEntity.badRequest().body("Invalid credentials");
	// 	}

	// 	// Check hashed password
	// 	if (!Repo.passwordEncoder.matches(password, user.getPassword())) {
	// 		return ResponseEntity.badRequest().body("Invalid credentials");
	// 	}
	// 	  // Create UserDetails without roles
	// 	UserDetails userDetails = org.springframework.security.core.userdetails.User
	// 	        .withUsername(user.getFullName())
	// 	        .password(user.getPassword())
	// 	        .authorities("User")
	// 	        .build();

	//     // Generate token
	//     String token = Repo.jwtUtils.generateTokenFromUsername(userDetails);

	//     Map<String, Object> response = new HashMap<>();
	//     response.put("token", token);
	//     response.put("message", "Login Successfully");

	// 	return ResponseEntity.ok(response);
	// }

}
