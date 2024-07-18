package src.model;

public class Crypto {

    /*  
     * Class that encrypt every data with the Caesar cipher
     */

    // Shift value for the Caesar cipher
    private static final int SHIFT = 3; 

    // Encrypts the text using the Caesar cipher
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

    // Decrypts the text using the Caesar cipher
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
