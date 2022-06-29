package com.wellnessenprevention.omao.email;

// we have used an interface because we can switch implementations
public interface EmailSender {

    void send(String to, String email);

}
