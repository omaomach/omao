package com.wellnessenprevention.omao.registration.tocken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationTocken, Long> {

    // what we need next is to find the confirmation token

}
