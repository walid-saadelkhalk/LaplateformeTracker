package src.model;

public class Crypto {


    private static final int SHIFT = 3; 

    public static String encrypt(String text) {
        StringBuilder result = new StringBuilder();
        for (char character : text.toCharArray()) {
            if (Character.isLetter(character)) {
                char base = Character.isLowerCase(character) ? 'a' : 'A';
                result.append((char) ((character - base + SHIFT) % 26 + base));
            } else {
                result.append(character);
            }
        }
        return result.toString();
    }

    public static String decrypt(String text) {
        StringBuilder result = new StringBuilder();
        for (char character : text.toCharArray()) {
            if (Character.isLetter(character)) {
                char base = Character.isLowerCase(character) ? 'a' : 'A';
                result.append((char) ((character - base - SHIFT + 26) % 26 + base));
            } else {
                result.append(character);
            }
        }
        return result.toString();
    }
    
}
