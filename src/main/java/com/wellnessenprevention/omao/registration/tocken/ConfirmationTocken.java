package com.wellnessenprevention.omao.registration.tocken;

import com.wellnessenprevention.omao.webuser.WebUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor

public class ConfirmationTocken {

    @Id
    @SequenceGenerator(
            name = "confirmation_token_sequence",
            sequenceName = "confirmation_token_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "confirmation_sequence"
    )

    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime confirmedAt;

    @ManyToOne // this is is because an application user can have many confirmation tokens
    @JoinColumn(
            nullable = false,
            name = "app_user_id"
    )
    // We need to tie the token to a user
    private WebUser webUser;

    public ConfirmationTocken(Long id, String token, LocalDateTime createdAt, LocalDateTime expiresAt, LocalDateTime confirmedAt, WebUser webUser) {
        this.id = id;
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.confirmedAt = confirmedAt;
        this.webUser = webUser;
    }
}
