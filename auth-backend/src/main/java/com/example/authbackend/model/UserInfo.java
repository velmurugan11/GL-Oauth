package com.example.authbackend.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name = "user_details")
public class UserInfo {
	  	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	  	
	  	@NotBlank
	  	@Email
	  	@Column(unique = true)
	    private String email;
	  	
	  	public UserInfo(){
	        
	    }
	    
	  	public UserInfo(String email) {
	        this.email = email;
	    }
	    
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}

		public String getEmail() { 
			return email; 
		}
	    public void setEmail(String email) { 
	    	this.email = email; 
	    }	    

	 }