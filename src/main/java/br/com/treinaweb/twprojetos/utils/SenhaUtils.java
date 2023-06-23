package br.com.treinaweb.twprojetos.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SenhaUtils {
    public static String encode(String senha){
        return new BCryptPasswordEncoder().encode(senha);
    }

    public static boolean matchs(String senha, String hash){
        return new BCryptPasswordEncoder().matches(senha, hash);
    }

}
