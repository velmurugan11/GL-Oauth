package com.example.authbackend.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name = "user_login")
public class Userlogin {
	  	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	  	
	  	@Column(unique = true)
	    private String email;
	    private String password;
	    
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
		
		public String getEmail() { return email; }
	    public void setEmail(String email) { this.email = email; }

	    public String getName() { return password; }
	    public void setPassword(String password) { this.password = password; }
	    

	 }
