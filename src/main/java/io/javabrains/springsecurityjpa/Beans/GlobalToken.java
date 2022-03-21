package io.javabrains.springsecurityjpa.Beans;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class GlobalToken  {


    private String token;
    private LocalDateTime date;


}
