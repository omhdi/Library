package com.maids.auth;

import com.maids.entities.CustomResponseEntity;
import com.maids.entities.Transactions;
import com.maids.exception.AuthException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/registor")
    public ResponseEntity<Object> register(@RequestBody RegisterRequest request){
        try{
            CustomResponseEntity<AuthinticationResponse> response = new CustomResponseEntity<>(HttpStatus.OK.toString(), "Success", Collections.singletonList(service.register(request)));
            return new ResponseEntity<>(response, HttpStatus.OK);
//            return ResponseEntity.ok(service.register(request));
        }catch (Exception e){
            throw new AuthException(e.toString());
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Object> authenticate(@Valid @RequestBody AuthenticationRequest request){
        try {
            CustomResponseEntity<AuthinticationResponse> response = new CustomResponseEntity<>(HttpStatus.OK.toString(), "Success", Collections.singletonList(service.authenticate(request)));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            throw new AuthException("wrong credentials");
        }
//        return ResponseEntity.ok(service.authenticate(request));
    }
}
