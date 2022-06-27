package com.wellnessenprevention.omao.webuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface WebUserRepository extends JpaRepository<WebUser, Long> {
    Optional<WebUser> findByEmail(String email);

}
