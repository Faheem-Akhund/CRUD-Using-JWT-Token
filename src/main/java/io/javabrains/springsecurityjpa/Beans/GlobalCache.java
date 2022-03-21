package io.javabrains.springsecurityjpa.Beans;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
public class GlobalCache  {


        public static List<GlobalToken> token=new ArrayList<>();

    public static void add(GlobalToken s)
    {
        token.add(s);
    }


}
