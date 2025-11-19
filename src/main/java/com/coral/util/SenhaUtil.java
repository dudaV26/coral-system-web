package com.coral.util;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class SenhaUtil {

    /**
     * Gera um hash BCrypt para a senha informada.
     * @param password Senha em texto.
     * @return Hash BCrypt da senha.
     */
    public static String hashPassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    /**
     * Verifica se uma senha em texto claro corresponde a um hash BCrypt.
     * @param password Senha em texto claro.
     * @param hashedPassword Hash BCrypt armazenado.
     * @return 
     */
    public static boolean checkPassword(String password, String hashedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);
        return result.verified;
    }
}