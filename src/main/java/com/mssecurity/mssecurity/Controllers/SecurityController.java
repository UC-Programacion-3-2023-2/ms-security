package com.mssecurity.mssecurity.Controllers;

import com.mssecurity.mssecurity.Models.User;
import com.mssecurity.mssecurity.Repositories.RoleRepository;
import com.mssecurity.mssecurity.Repositories.UserRepository;
import com.mssecurity.mssecurity.Services.EncryptionService;
import com.mssecurity.mssecurity.Services.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("security")
public class SecurityController {
    @Autowired
    private UserRepository theUserRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private EncryptionService encryptionService;

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

}
