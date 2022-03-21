package io.javabrains.springsecurityjpa.Beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusBean {

    private Integer code;
    private String message;
    private Object object;

    public StatusBean(Integer code, String message, Object object) {
        this.code = code;
        this.message = message;
        this.object = object;
    }


}
