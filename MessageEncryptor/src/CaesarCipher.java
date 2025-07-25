public class CaesarCipher implements Cipher {
    private final char[] plain = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    private final char[] caesar = {
            'X', 'Y', 'Z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W'
    };

    public String encrypt(String message) {
        message = message.toUpperCase();
        StringBuilder encrypted = new StringBuilder();

        for (char ch : message.toCharArray()) {
            if (ch >= 'A' && ch <= 'Z') {
                for (int i = 0; i < plain.length; i++) {
                    if (ch == plain[i]) {
                        encrypted.append(caesar[i]);
                        break;
                    }
                }
            } else {
                encrypted.append(ch); // Keep spaces and punctuation
            }
        }

        return encrypted.toString();
    }
}
