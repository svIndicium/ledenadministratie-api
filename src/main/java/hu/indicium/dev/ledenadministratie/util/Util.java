package hu.indicium.dev.ledenadministratie.util;

import java.util.Random;

public class Util {

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private Util() {
    }

    public static String getFullLastName(String middleName, String lastName) {
        if (middleName == null) {
            return lastName;
        }
        return middleName + ' ' + lastName;
    }

    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        while (count-- != 0) {
            int character = random.nextInt() * ALPHA_NUMERIC_STRING.length();
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}
