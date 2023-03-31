package com.switchfully.eurder.user.service.security;

import com.switchfully.eurder.user.domain.repository.UserRepository;
import com.switchfully.eurder.user.domain.userObject.User;
import com.switchfully.eurder.user.service.exceptions.UnauthorizedException;
import com.switchfully.eurder.user.service.exceptions.WrongPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class SecurityService {
    private final UserRepository userRepository;

    @Autowired
    public SecurityService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public int validateAuthorization(String authorization, Feature feature){
        Credential decodedAuthorization = decodeAuth(authorization);
        User user = userRepository.getAUserByEmail(decodedAuthorization.email());

        if(!user.doesPasswordMatch(decodedAuthorization.password())){
            throw new WrongPasswordException("Wrong password. Please try again.");
        }
        if(!user.canHaveAccessTo(feature)){
            throw new UnauthorizedException("Permission denied.");
        }
        return user.getUserID();
    }

    private Credential decodeAuth(String authorization){
        String decodedAuthorization = new String(Base64.getDecoder().decode(authorization.substring("Basic ".length())));
        String email = decodedAuthorization.substring(0, decodedAuthorization.indexOf(":"));
        String password = decodedAuthorization.substring(decodedAuthorization.indexOf(":")+1);
        return new Credential(email, password);
    }
}
