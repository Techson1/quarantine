package com.beiqisoft.aoqun.security;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.config.GlobalConfig;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.User;

@RestController
public class AuthController {
    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthService authService;
   
    //@JSON(type=User.class,filter="role")
    @RequestMapping(value = "${jwt.route.authentication.path}", method = RequestMethod.POST)
    public Map<String,Object> createAuthenticationToken(JwtAuthenticationRequest authenticationRequest) throws AuthenticationException{
    	final String token = authService.login(authenticationRequest.getUsername(), authenticationRequest.getPassword());
    	User user = authService.getUserByname(authenticationRequest.getUsername());
    	Map<String,Object> map  = new HashMap<String, Object>();
    	map.put("token",token);
    	map.put("user",user);
    	map.put("message",100);
    	if(user.getState()!=null&&user.getState()==0) map.put("message",101);
    	return map;
    }
    
    @RequestMapping(value = "${jwt.route.authentication.refresh}", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) throws AuthenticationException{
    	String token = request.getHeader(tokenHeader);
        String refreshedToken = authService.refresh(token);
        if(refreshedToken == null) {
            return ResponseEntity.badRequest().body(null);
        } else {
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        }
    }
    @RequestMapping(value = "${jwt.route.authentication.register}")
    public Message register(User addedUser) throws AuthenticationException{
    	authService.register(addedUser);
        return GlobalConfig.SUCCESS;
    }
}
