package com.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.entity.signup;

@Repository
public interface SignupLoginRepository extends JpaRepository<signup,Long> {

	@Query(value="select * FROM signup where user_id=:user_id", nativeQuery=true)
	String findByUserId(@Param("user_id") String user_id);

	@Query(value="select * FROM signup where email_id=:email", nativeQuery=true)
	signup findByEmailId(@Param("email") String email);

	
	signup findByMobileNumber(String mobileNumber);
	

}
