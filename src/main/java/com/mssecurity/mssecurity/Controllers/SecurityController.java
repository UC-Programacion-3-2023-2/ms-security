package com.mssecurity.mssecurity.Controllers;

import com.mssecurity.mssecurity.Models.Permission;
import com.mssecurity.mssecurity.Models.User;
import com.mssecurity.mssecurity.Repositories.UserRepository;
import com.mssecurity.mssecurity.Services.EncryptionService;
import com.mssecurity.mssecurity.Services.JwtService;
import com.mssecurity.mssecurity.Services.ValidatorsService;
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
    @Autowired
    private ValidatorsService validatorService;

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
        User theUser=this.validatorService.getUser(request);
        return theUser;
    }
    @PostMapping("permissions-validation")
    public boolean permissionsValidation(final HttpServletRequest request,@RequestBody Permission thePermission) {
        boolean success=this.validatorService.validationRolePermission(request,thePermission.getUrl(),thePermission.getMethod());
        return success;
    }
}
