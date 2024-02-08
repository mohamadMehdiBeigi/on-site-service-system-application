package ir.example.finalPart03.util;

import java.util.Random;

public class RandomStringGenerator {
    public static String randomGenerator(){
        String charSet = "0123456789";
        StringBuilder result = new StringBuilder();
        Random rand = new Random();

        while (result.length() < 12) {
            int index = (int) (rand.nextFloat() * charSet.length());
            result.append(charSet.charAt(index));
        }

        String randomString = result.toString();
        System.out.println(randomString);

        return randomString;
    }
}