package com.datnguyen.Utils;
import io.github.cdimascio.dotenv.Dotenv;

public class GetEnv {
    public String get(String key) {
        String value = System.getenv(key);
        if (value == null) {
            Dotenv dotenv = Dotenv.load();
            value = dotenv.get(key);
        }
        return value;
    }
}
