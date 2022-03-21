package io.javabrains.springsecurityjpa.JWT;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserToken {

    private String username;
    private String jwtToken;
    private String refreshToken;
}
