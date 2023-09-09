package com.mssecurity.mssecurity.Controllers;

import com.mssecurity.mssecurity.Models.Role;
import com.mssecurity.mssecurity.Models.User;
import com.mssecurity.mssecurity.Repositories.RoleRepository;
import com.mssecurity.mssecurity.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
@CrossOrigin
@RestController
@RequestMapping("api/users")
public class UsersController {
    @Autowired
    private UserRepository theUserRepository;
    @Autowired
    private RoleRepository theRoleRepository;

    @GetMapping("")
    public List<User> index() {return this.theUserRepository.findAll();}

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User store(@RequestBody User newUser) {
        newUser.setPassword(this.convertSHA256(newUser.getPassword()));
        return this.theUserRepository.save(newUser);
    }

    @GetMapping("{id}")
    public User show(@PathVariable String id) {
        User theUser = this.theUserRepository
                .findById(id)
                .orElse(null);
        return theUser;
    }

    @PutMapping("{id}")
    public User update(@PathVariable String id, @RequestBody User theNewUser) {
        User theActualUser = this.theUserRepository
                .findById(id)
                .orElse(null);
        if (theActualUser != null) {
            theActualUser.setName(theNewUser.getName());
            theActualUser.setEmail(theNewUser.getEmail());
            theActualUser.setPassword(this.convertSHA256(theNewUser.getPassword()));
            return this.theUserRepository.save(theActualUser);
        } else {
            return null;
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void destroy(@PathVariable String id) {
        User theUser = this.theUserRepository
                .findById(id)
                .orElse(null);
        if (theUser != null) {
            this.theUserRepository.delete(theUser);
        }
    }

    @PutMapping("{user_id}/role/{role_id}")
    public User matchUserRole(@PathVariable String user_id,
                              @PathVariable String role_id) {
        User theActualUser = this.theUserRepository.findById(user_id)
                .orElse(null);
        Role theActualRole = this.theRoleRepository.findById(role_id)
                .orElse(null);
        if (theActualUser != null && theActualRole != null) {
            theActualUser.setRole(theActualRole);
            return this.theUserRepository.save(theActualUser);
        } else {
            return null;
        }
    }
    @PutMapping("{user_id}/role")
    public User unMatchUserRole(@PathVariable String user_id) {
        User theActualUser = this.theUserRepository.findById(user_id)
                .orElse(null);
        if (theActualUser != null) {
            theActualUser.setRole(null);
            return this.theUserRepository.save(theActualUser);
        } else {
            return null;
        }
    }
    public String convertSHA256(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        byte[] hash = md.digest(password.getBytes());
        StringBuffer sb = new StringBuffer();
        for(byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
