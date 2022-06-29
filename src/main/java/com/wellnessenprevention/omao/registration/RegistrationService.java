package com.wellnessenprevention.omao.registration;

import com.wellnessenprevention.omao.webuser.WebUser;
import com.wellnessenprevention.omao.webuser.WebUserRole;
import com.wellnessenprevention.omao.webuser.WebUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final WebUserService webUserService;
    private final EmailValidator emailValidator;
    public String register(RegistrationRequest registrationRequest) {
        boolean isValid = emailValidator.test(registrationRequest.getEmail());

        if (!isValid) {
            throw new IllegalStateException("The email provided is not valid");
        }

        return webUserService.signUpUser(
                new WebUser(

                        registrationRequest.getFirstName(),
                        registrationRequest.getLastName(),
                        registrationRequest.getEmail(),
                        registrationRequest.getPassword(),
                        WebUserRole.USER

                )
        );
    }
}
