package io.javabrains.springsecurityjpa.JWT;

import io.javabrains.springsecurityjpa.User.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;




@Setter
@Getter
@Entity
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;
}
