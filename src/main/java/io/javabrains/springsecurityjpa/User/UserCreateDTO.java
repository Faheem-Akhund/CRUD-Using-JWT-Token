package io.javabrains.springsecurityjpa.User;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserCreateDTO {

    private String userName;

    private String password;

    private String roles;
}
