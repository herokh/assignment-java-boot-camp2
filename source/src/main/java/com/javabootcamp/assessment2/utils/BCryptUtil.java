package com.javabootcamp.assessment2.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class BCryptUtil {
    public static String hashString(String rawValue) {
        String hashString = BCrypt.withDefaults().hashToString(12, rawValue.toCharArray());
        return hashString;
    }

    public static Boolean validateHashString(String rawValue, String hashString) {
        BCrypt.Result result = BCrypt.verifyer().verify(rawValue.toCharArray(), hashString);
        return result.verified;
    }
}
