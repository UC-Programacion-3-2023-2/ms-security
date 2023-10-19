package com.mssecurity.mssecurity.Controllers;

import com.mssecurity.mssecurity.Models.User;
import com.mssecurity.mssecurity.Repositories.UserRepository;
import com.mssecurity.mssecurity.Services.EncryptionService;
import com.mssecurity.mssecurity.Services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("api/public/security")
public class SecurityController {
    @Autowired
    private UserRepository theUserRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private EncryptionService encryptionService;
    private static final String BEARER_PREFIX = "Bearer ";
    //Método login
    @PostMapping("login")
    public String login(@RequestBody User theUser, final HttpServletResponse response) throws IOException {
        String token="";
        User actualUser=this.theUserRepository.getUserByEmail(theUser.getEmail());
        if(actualUser!=null && actualUser.getPassword().equals(encryptionService.convertSHA256(theUser.getPassword()))){
            //Generar token
            token=jwtService.generateToken(actualUser);
        }else{
            //manejar el problema
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
        return token;
    }


    //Método logout
    //Método reset pass
    @GetMapping("token-validation")
    public User tokenValidation(final HttpServletRequest request) {
        User theUser=null;
        String authorizationHeader = request.getHeader("Authorization");
        System.out.println("Header "+authorizationHeader);
        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
            String token = authorizationHeader.substring(BEARER_PREFIX.length());
            System.out.println("Bearer Token: " + token);
            User theUserFromToken=jwtService.getUserFromToken(token);
            if(theUserFromToken!=null) {
                theUser= this.theUserRepository.findById(theUserFromToken.get_id())
                        .orElse(null);
                theUser.setPassword("");
            }
        }
        return theUser;
    }
}
