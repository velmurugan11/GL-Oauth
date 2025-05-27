package com.example.authbackend.controller;

import com.example.authbackend.model.UserInfo;
import com.example.authbackend.model.Userlogin;
import com.example.authbackend.repo.UserLoginRepo;
//import com.example.authbackend.repo.userInfoRepo;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.example.authbackend.model.Userlogin;
import com.google.api.client.json.jackson2.JacksonFactory;
//import com.google.api.client.json.jackson2.JacksonFactory;

//import com.google.api.client.json.jackson2.JacksonFactory;



import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "http://localhost:3000")
public class Userlogincontroller {



	@Autowired
    private UserLoginRepo UserLoginRepo;

	
	@GetMapping("/")
	public String home() { return "Welcome"; }

    @GetMapping("/userlogin")
    public List<Userlogin> getAllUsers() {
        return UserLoginRepo.findAll();
    }

    @PostMapping("/userlogin")
    public Userlogin addUser(@RequestBody Userlogin user) {
        return UserLoginRepo.save(user);
    }
    
    
    @PostMapping("/google")
    public ResponseEntity<?> googleLogin(@RequestBody Map<String, String> body) {
        String idTokenString = body.get("token");

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
            new NetHttpTransport(), JacksonFactory.getDefaultInstance())
            .setAudience(Collections.singletonList("google-Oauth-Token"))
            .build();

        try {
            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                Payload payload = idToken.getPayload();
                String email = payload.getEmail();

                Userlogin user = UserLoginRepo.findByEmail(email).orElse(null);

                if (user == null) {
                    Userlogin newUser = new Userlogin();
                    newUser.setEmail(email);
                    newUser.setPassword(""); // optional for Google login
                    newUser.setLastLogin(LocalDateTime.now());
                    user = UserLoginRepo.save(newUser);
                } else {
                    user.setLastLogin(LocalDateTime.now());
                    user = UserLoginRepo.save(user);
                }

                return ResponseEntity.ok("Success");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Auth error");
        }
    }
}

//@PostMapping("/google")
//public ResponseEntity<?> googleLogin(@RequestBody Map<String, String> body) {
//  String idTokenString = body.get("token");
//
//  GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
//          .setAudience(Collections.singletonList("279136439919-v4hrnqqvqfol2ufpe51g5da6po7el5hf.apps.googleusercontent.com"))
//          .build();
//
//  try {
//      GoogleIdToken idToken = verifier.verify(idTokenString);
//      if (idToken != null) {
//          Payload payload = idToken.getPayload();
//
//          String email = payload.getEmail();
//          String password = (String) payload.get("password");
//
//          Userlogin user = UserLoginRepo.findByEmail(email).orElseGet(() -> {
//              Userlogin newUser = new Userlogin();
//              newUser.setEmail(email);
//              newUser.setPassword(password);
//              return UserLoginRepo.save(newUser);
//          });
//
//          return ResponseEntity.ok("Success");
//      } else {
//          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
//      }
//  } catch (Exception e) {
//      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Auth error");
//  }
//}

//==============
//package com.example.authbackend.controller;
//
//import com.example.authbackend.model.UserInfo;
//import com.example.authbackend.model.Userlogin;
//import com.example.authbackend.repo.UserInfoRepo;
//import com.example.authbackend.repo.UserLoginRepo;
//import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
//import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
//import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.jackson2.JacksonFactory;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDateTime;
//import java.util.*;
//
//@CrossOrigin(origins = "http://localhost:5173")
//@RestController
//@RequestMapping("/api")
//public class Userlogincontroller {
//
//    @Autowired
//    private UserLoginRepo userLoginRepo;
//
//    @Autowired
//    private UserInfoRepo userInfoRepo;
//
//    @GetMapping("/status")
//    public String home() {
//        return "API is running!";
//    }
//
//    @GetMapping("/userlogin")
//    public List<Userlogin> getAllUsers() {
//        return userLoginRepo.findAll();
//    }
//
//    @PostMapping("/userlogin")
//    public Userlogin addUser(@RequestBody Userlogin user) {
//        return userLoginRepo.save(user);
//    }
//
//    // ✅ Google Login with smart handling for new vs existing user
//    @PostMapping("/google")
//    public ResponseEntity<?> googleLogin(@RequestBody Map<String, String> body) {
//        String idTokenString = body.get("token");
//
//        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
//                new NetHttpTransport(),
//                JacksonFactory.getDefaultInstance()
//        )
//        .setAudience(Collections.singletonList("279136439919-v4hrnqqvqfol2ufpe51g5da6po7el5hf.apps.googleusercontent.com"))
//        .build();
//
//        try {
//            GoogleIdToken idToken = verifier.verify(idTokenString);
//            if (idToken != null) {
//                Payload payload = idToken.getPayload();
//                String email = payload.getEmail();
//
//                Optional<Userlogin> userOpt = userLoginRepo.findByEmail(email);
//
//                if (userOpt.isPresent()) {
//                    Userlogin existingUser = userOpt.get();
//                    existingUser.setLastLogin(LocalDateTime.now());
//                    userLoginRepo.save(existingUser);
//
//                    System.out.println("User logged in: " + email);
//                    return ResponseEntity.ok(Map.of("status", "existing_user"));
//                } else {
//                    // Register new user with minimal info
//                    Userlogin newUser = new Userlogin();
//                    newUser.setEmail(email);
//                    newUser.setPassword(""); // Set a placeholder or use a random UUID
//                    newUser.setLastLogin(LocalDateTime.now());
//                    userLoginRepo.save(newUser);
//
//                    userInfoRepo.save(new UserInfo(email));
//
//                    System.out.println("New user registered: " + email);
//                    return ResponseEntity.ok(Map.of("status", "new_user", "email", email));
//                }
//            } else {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Auth error");
//        }
//    }
//
//    // ✅ Complete signup with name and password
//    @PostMapping("/signup")
//    public ResponseEntity<?> completeSignup(@RequestBody Map<String, String> data) {
//        String email = data.get("email");
//        String name = data.get("name");
//        String password = data.get("password");
//        String confirmPassword = data.get("confirmPassword");
//
//        if (!password.equals(confirmPassword)) {
//            return ResponseEntity.badRequest().body("Passwords do not match");
//        }
//
//        Optional<Userlogin> userOpt = userLoginRepo.findByEmail(email);
//        if (userOpt.isPresent()) {
//            Userlogin user = userOpt.get();
//            user.setname(name);
//            user.setPassword(password); // 🔐 In production, hash this with BCrypt or similar
//            userLoginRepo.save(user);
//
//            System.out.println("Signup completed for: " + email);
//            return ResponseEntity.ok("Signup completed");
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email not found. Please login with Google first.");
//        }
//    }
//}
