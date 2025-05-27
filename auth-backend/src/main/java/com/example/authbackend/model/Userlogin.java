package com.example.authbackend.model;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name = "user_login")
public class Userlogin {
	  	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	  	private String username;
	  	@Column(unique = true)
	    private String email;
	    private String password;
	    
	    @Column(name = "lastlogin")
	    private LocalDateTime lastlogin;
	    
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
//		public String getEmail() {
//			return email;
//		}
//		public void setEmail(String email) {
//			this.email = email;
//		}
//		public String getPassword() {
//			return password;
//		}
//		public void setPassword(String password) {
//			this.password = password;
//		}
		
		public String getname() { return username; }
	    public void setname(String username) { this.username = username; }
		
		public String getEmail() { return email; }
	    public void setEmail(String email) { this.email = email; }

	    public String getName() { return password; }
	    public void setPassword(String password) { this.password = password; }
	    
	    public LocalDateTime getLastLogin() {
	        return lastlogin;
	    }

	    public void setLastLogin(LocalDateTime lastLogin) {
	        this.lastlogin = lastLogin;
	    }
	    

	 }