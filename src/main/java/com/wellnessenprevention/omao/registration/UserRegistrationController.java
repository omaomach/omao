package com.wellnessenprevention.omao.registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class UserRegistrationController {

    private RegistrationService registrationService;

    public String register(@RequestBody RegistrationRequest registrationRequest) {

        return registrationService.register(registrationRequest);
    }

}
