package com.wellnessenprevention.omao.registration;

import com.wellnessenprevention.omao.registration.token.ConfirmationToken;
import com.wellnessenprevention.omao.registration.token.ConfirmationTokenService;
import com.wellnessenprevention.omao.webuser.WebUser;
import com.wellnessenprevention.omao.webuser.WebUserRole;
import com.wellnessenprevention.omao.webuser.WebUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final WebUserService webUserService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;

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

    @Transactional
    public String confirmToken(String token) {

        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        webUserService.enableWebUser(
                confirmationToken.getWebUser().getEmail());
        return "confirmed";
    }

}
