package com.backend.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Entity
@Table(name="signup")
public class signup {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String user_id; 
private String fullName;
private String EmailId;
private String Password;
private String mobileNumber;
private LocalDateTime create_at;

@PrePersist
@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
protected void createDate() {
    this.create_at = LocalDateTime.now();  
} 

public String generateTemplateIdWithUUID() {
    String prefix = "S";
    String uuid = UUID.randomUUID().toString().replace("-", ""); // Remove dashes for a cleaner output
    user_id = prefix + "-" + uuid.substring(0, 8);  // Limiting UUID to 16 characters
    return user_id;
}

}
