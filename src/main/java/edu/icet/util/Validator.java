package edu.icet.util;

import org.mindrot.jbcrypt.BCrypt;

public class Validator {

    public boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
    public String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }
    public boolean checkPassword(String plainPassword,String hashedPassword){
        if (plainPassword!=null&&hashedPassword!=null){
            return BCrypt.checkpw(plainPassword,hashedPassword);
        }
        return false;
    }
}
