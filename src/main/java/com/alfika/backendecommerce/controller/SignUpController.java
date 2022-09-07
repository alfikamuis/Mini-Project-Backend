package com.alfika.backendecommerce.controller;

import com.alfika.backendecommerce.dto.SignUpDTO;
import com.alfika.backendecommerce.model.ERole;
import com.alfika.backendecommerce.model.Role;
import com.alfika.backendecommerce.model.User;
import com.alfika.backendecommerce.repository.RoleRepository;
import com.alfika.backendecommerce.repository.UserRepository;
import com.alfika.backendecommerce.response.SignUpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class SignUpController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    RoleRepository roleRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(
            @Valid @RequestBody SignUpDTO signUpDTO){

        //check duplication of username and email
        if(userRepository.existsByUsername(signUpDTO.getUsername())) {
            return ResponseEntity.badRequest().body(new SignUpResponse("Error: Username is already taken!"));
        }
        if(userRepository.existsByEmail(signUpDTO.getEmail())) {
            return ResponseEntity.badRequest().body(new SignUpResponse("Error: Email is already in use!"));
        }

        //Create User's Account
        User user=new User(
                signUpDTO.getUsername(),
                signUpDTO.getEmail(),
                signUpDTO.getAddress(),
                encoder.encode(signUpDTO.getPassword())

        );

        //default signIn record as User
        Set<String> rolesfromRequest = signUpDTO.getRole();
        Set<Role> roles = new HashSet<>();
        if(rolesfromRequest==null)
        {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(()->new RuntimeException("Error! not found."));
            roles.add(userRole);
        }
        else
        {
            rolesfromRequest.forEach(role ->{
                switch (role) {
                    case "admin":
                        Role adminRole=roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(()-> new RuntimeException("Error! not found."));
                        roles.add(adminRole);
                        break;

                    default:
                        Role userRole=roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error! not found."));
                        roles.add(userRole);
                        break;
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new SignUpResponse(
                "User <"+user.getUsername()
                        +"> email: <"+user.getEmail()
                        +"> registered!"));

    }
}
