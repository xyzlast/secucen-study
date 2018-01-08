package com.xyzlast.bookstore.ac;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Hello {
    private String name;
    public String sayHello() {
        return "Hello, " + name;
    }
}
