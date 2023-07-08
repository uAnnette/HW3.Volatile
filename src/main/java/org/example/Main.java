package org.example;

import java.util.Random;

public class Main {
    public static volatile long lengthThree = 0;
    public static volatile long lengthFour = 0;
    public static volatile long lengthFive = 0;

    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread threadOneLetter = new Thread(() -> {
            for (String text : texts) {
                if (criterionSameness(text)) {
                    lengthScore(text);
                }
            }
        });
        threadOneLetter.start();
        threadOneLetter.join();

        Thread threadPalindrome = new Thread(() -> {
            for (String text : texts) {
                if (text.equals(new StringBuilder(text).reverse().toString()) && !criterionSameness(text)) {
                    lengthScore(text);
                }
            }
        });
        threadPalindrome.start();
        threadPalindrome.join();

        Thread threadAlphabetical = new Thread(() -> {
            for (String text : texts) {
                if (alphabeticalOrder(text) && !criterionSameness(text)) {
                    lengthScore(text);
                }
            }
        });
        threadAlphabetical.start();
        threadAlphabetical.join();

        System.out.println("Красивых слов с длиной 3: " + lengthThree + " шт." +
                "\nКрасивых слов с длиной 4: " + lengthFour + " шт." +
                "\nКрасивых слов с длиной 5: " + lengthFive + " шт.");
    }

    public static boolean alphabeticalOrder(String texts) {
        int y = 1;
        for (int i = 0; i < texts.length() - 1; i++) {
            if (texts.charAt(i) <= texts.charAt(i + 1)) {
                y++;
            }
        }
        return y == texts.length();
    }

    public static boolean criterionSameness(String texts) {
        int x = 1;
        char a = texts.charAt(0);
        for (int i = 1; i < texts.length(); i++) {
            if (texts.charAt(i) == a) {
                x++;
            }
        }
        return x == texts.length();
    }

    public static void lengthScore(String text) {
        if (text.length() == 3) {
            lengthThree++;
        }
        if (text.length() == 4) {
            lengthFour++;
        }
        if (text.length() == 5) {
            lengthFive++;
        }
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}