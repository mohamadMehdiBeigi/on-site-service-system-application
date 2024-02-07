package ir.example.finalPart03.config;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Random;

public class PasswordGenerator {
    private static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String lower = upper.toLowerCase(Locale.ROOT);
    private static final String digits = "0123456789";
    private static final String specialChars = "@#$%&";
    private static final String alphanum = upper + lower + digits + specialChars;
    private final Random random;
    private final char[] symbols;
    private final char[] buf;

    public PasswordGenerator(int length) {
        if (length < 1) throw new IllegalArgumentException();

        this.random = new SecureRandom();
        this.symbols = alphanum.toCharArray();
        this.buf = new char[length];
    }

    public String nextPassword() {
        assert buf.length >= 8 : "The length of the password should be at least 8 characters";

        // at least one character of each type
        buf[0] = upper.charAt(random.nextInt(upper.length()));
        buf[1] = lower.charAt(random.nextInt(lower.length()));
        buf[2] = digits.charAt(random.nextInt(digits.length()));
        buf[3] = specialChars.charAt(random.nextInt(specialChars.length()));

        // fill in remaining with random characters
        for (int i = 4; i < buf.length; ++i) {
            buf[i] = symbols[random.nextInt(symbols.length)];
        }

        // shuffle the characters
        for (int i = 0; i < buf.length; i++) {
            int randomPosition = random.nextInt(buf.length);
            char temp = buf[i];
            buf[i] = buf[randomPosition];
            buf[randomPosition] = temp;
        }

        return new String(buf);
    }
}