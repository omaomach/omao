package com.wellnessenprevention.omao.webuser;

import com.wellnessenprevention.omao.registration.token.ConfirmationToken;
import com.wellnessenprevention.omao.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

// How we find users once we try to log in
@Service
@AllArgsConstructor
public class WebUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
    private final WebUserRepository webUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return webUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    // Method to sign up users
    public String signUpUser(WebUser webUser) {
        boolean userExits = webUserRepository.findByEmail(webUser.getEmail())
                .isPresent();

        if (userExits) {
            throw new IllegalStateException("email already taken");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(webUser.getPassword());

        webUser.setPassword(encodedPassword);

        webUserRepository.save(webUser);

        String token = UUID.randomUUID().toString();
        // TODO: Send confirmation token
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                webUser

        );

        confirmationTokenService.saveConfirmationToken(
                confirmationToken
        );

        //TODO: Send email

        return token;
    }

    public int enableWebUser(String email) {

        return webUserRepository.enableWebUser(email);
    }

}
