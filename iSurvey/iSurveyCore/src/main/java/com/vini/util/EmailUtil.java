package com.vini.util;

public class EmailUtil {
    
    private static final int M_FACTOR = 3;
    private static final int S_FACTOR = 7;
    
    private EmailUtil() {
        
    }
    
    /**
     * Method to encrypt email id
     * @param emailId
     * @return encrypted email id
     */
    public static String encryptEmailId(String emailId){
        char[] letters = emailId.toCharArray();
        StringBuilder encriptedEmailId = new StringBuilder();
        
        for (int index = 0; index <letters.length; index++) {
            int code = letters[index];
            encriptedEmailId.append((char) ((code * M_FACTOR) - S_FACTOR));
        }
        return encriptedEmailId.toString();
    }
    
    /**
     * Method to decrypt email id
     * @param emailId
     * @return decrypted email id
     */
    public static String decryptEmailId(String encryptedEmailId) {
        char[] letters = encryptedEmailId.toCharArray();
        StringBuilder decriptedEmailId = new StringBuilder();
        
        for (int index = 0; index <letters.length; index++) {
            int code = letters[index];
            decriptedEmailId.append((char) ((code + S_FACTOR) / M_FACTOR));
        }
        return decriptedEmailId.toString();
    }
    
}
