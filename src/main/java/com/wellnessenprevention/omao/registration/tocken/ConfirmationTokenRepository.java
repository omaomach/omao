package com.wellnessenprevention.omao.registration.tocken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    // what we need next is to find the confirmation token

    Optional<ConfirmationToken> findByToken(String token);

}
