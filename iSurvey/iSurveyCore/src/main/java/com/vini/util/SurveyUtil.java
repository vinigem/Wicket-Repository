package com.vini.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SurveyUtil {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SurveyUtil.class);
    
    private static final int M_FACTOR = 3;
    private static final int S_FACTOR = 7;
    
    private SurveyUtil() {
        
    }
    
    /**
     * Method encrypt URID
     * @param registrationId
     * @return encrypted URID
     */
    public static String encryptURID(long registrationId){
        char[] characters = String.valueOf(registrationId).toCharArray();
        StringBuilder encryptedURID = new StringBuilder();
        
        for(int index = 0; index < characters.length; index++){
            int code = characters[index];
            char ch = (char) ((code * M_FACTOR) - S_FACTOR);
            encryptedURID.append(Integer.toHexString(ch));
            
            if((index + 1) % 4 == 0){
                encryptedURID.append("-");
            }
        }
        return encryptedURID.toString();
    }
    
    /**
     * Method decrypt URID
     * @param registrationId
     * @return decrypted URID
     */
    public static long decryptURID(String registrationId){
        if(registrationId == null){
            return 0;
        }
        StringBuilder decriptedURID = new StringBuilder();
        String regId = registrationId.replace("-", "");
        for (int index = 0; index < regId.length(); index += 2) {
            String str = regId.substring(index, index + 2);
            try{
                char code = (char) Integer.parseInt(str, 16);
                decriptedURID.append((char) ((code + S_FACTOR) / M_FACTOR));
            }catch(NumberFormatException e){
                LOGGER.error("Invalid hex code {} ", str);
                return 0;
            }
        }
        try{
            return Long.valueOf(decriptedURID.toString());
        }catch(NumberFormatException e){
            LOGGER.error("Error while decrypting URID. {}", e);
            return 0;
        }
    }
    
}
