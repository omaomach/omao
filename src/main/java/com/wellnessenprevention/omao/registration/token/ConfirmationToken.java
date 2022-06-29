package com.wellnessenprevention.omao.registration.token;

import com.wellnessenprevention.omao.webuser.WebUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConfirmationToken {

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
    private LocalDateTime conformedAt;

    @ManyToOne // this is because an application user can have many confirmation tokens
    @JoinColumn(
            nullable = false,
            name = "app_user_id"
    )
    // We need to tie the token to a user
    private WebUser webUser;
    public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt, WebUser webUser) {
        this.id = id;
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.webUser = webUser;
    }


    public Object getConfirmedAt() {
        return null;
    }
}
