package com.backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.backend.Repository.*;
import com.backend.entity.*;
@RestController
public class SignupLogin {
	
	@Autowired
	private SignupLoginRepository signupLoginRepo;
	
	   @Autowired
	    private PasswordEncoder passwordEncoder;

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody signup signup){
		String user_id=signup.generateTemplateIdWithUUID();
		String email=signup.getEmailId();
		String existId = signupLoginRepo.findByUserId(user_id);
		signup existEmail=null;
		existEmail=signupLoginRepo.findByEmailId(email);
				if (existId != null) {
			user_id = signup.generateTemplateIdWithUUID();
		}
				if(existEmail!=null) {
					return ResponseEntity.badRequest().body("email already exist");
				}
		signup.setUser_id(user_id);
		if(signup.getMobileNumber()==null ||  !signup.getMobileNumber().matches("\\d{10}")) {
			 return ResponseEntity.badRequest().body("Invalid mobile number. It must be 10 digits.");
		}
		
		 String hashedPassword = passwordEncoder.encode(signup.getPassword());
	        signup.setPassword(hashedPassword);
	        
		return ResponseEntity.ok(signupLoginRepo.save(signup));
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody signup login) {
	    String email = login.getEmailId();
	    String mobileNumber = login.getMobileNumber();
	    String password = login.getPassword();

	    signup user = null;

	    if (email != null && !email.isEmpty()) {
	        user = signupLoginRepo.findByEmailId(email);
	    } else if (mobileNumber != null && !mobileNumber.isEmpty()) {
	        user = signupLoginRepo.findByMobileNumber(mobileNumber);
	    } else {
	        return ResponseEntity.badRequest().body("Email or Mobile Number is required");
	    }

	    if (user == null) {
	        return ResponseEntity.badRequest().body("Invalid credentials");
	    }

	    // Check hashed password
	    if (!passwordEncoder.matches(password, user.getPassword())) {
	        return ResponseEntity.badRequest().body("Invalid credentials");
	    }

	    return ResponseEntity.ok("Login Successfully");
	}

}
