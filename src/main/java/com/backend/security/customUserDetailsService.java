package com.backend.security;

import com.backend.Repository.SignupLoginRepository;
import com.backend.entity.signup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class customUserDetailsService implements UserDetailsService {

    @Autowired
    private SignupLoginRepository signupRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        signup user = signupRepository.findByUserId(username);
if (user == null) {
    throw new UsernameNotFoundException("User not found");
}

        return new User(
                user.getUser_id(),
                user.getPassword(),
                Collections.emptyList() // no roles
        );
    }
}
