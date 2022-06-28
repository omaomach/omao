package com.wellnessenprevention.omao.webuser;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

// How we find users once we try to log in
@Service
@AllArgsConstructor
public class WebUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
    private final WebUserRepository webUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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

        // TODO: Send confirmation token

        return "";
    }

}
