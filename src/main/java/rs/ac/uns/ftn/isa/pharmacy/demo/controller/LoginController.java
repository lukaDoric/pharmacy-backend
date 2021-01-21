package rs.ac.uns.ftn.isa.pharmacy.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.isa.pharmacy.demo.model.User;
import rs.ac.uns.ftn.isa.pharmacy.demo.model.dto.UserTokenState;
import rs.ac.uns.ftn.isa.pharmacy.demo.security.TokenUtils;
import rs.ac.uns.ftn.isa.pharmacy.demo.security.auth.JwtAuthenticationRequest;
import rs.ac.uns.ftn.isa.pharmacy.demo.service.impl.CustomUserDetailsService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginController {

    private TokenUtils tokenUtils;
    private AuthenticationManager authenticationManager;
    private CustomUserDetailsService userDetailsService;

    @Autowired
    public LoginController(TokenUtils tokenUtils, AuthenticationManager authenticationManager,
                           CustomUserDetailsService userDetailsService) {
        this.tokenUtils = tokenUtils;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/")
    public ResponseEntity<UserTokenState> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
                                                                    HttpServletResponse response) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        String username = user.getUsername();
        String userType = user.getClass().getSimpleName();
        String accessToken = tokenUtils.generateToken(username);
        int accessExpiresIn = tokenUtils.getAccessTokenExpiresIn();
        String refreshToken = tokenUtils.generateRefreshToken(username);
        int refreshExpiresIn = tokenUtils.getRefreshTokenExpiresIn();
        UserTokenState state = new UserTokenState(userType, accessToken, refreshToken, accessExpiresIn, refreshExpiresIn);
        return ResponseEntity.ok(state);
    }
}
