package oop.ex6.main;

import java.io.IOException;

public class Ex6exception extends IOException {
    /**
     * constructs an Ex6exception with an error detail message
     * @param message
     */
    public Ex6exception(String message) {
        super(message);
    }
}
