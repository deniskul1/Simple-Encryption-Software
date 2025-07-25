public class VigenereCipher implements Cipher {
  private final String key;

  public VigenereCipher(String key) {
    this.key = key.toUpperCase();
  }

  public String encrypt(String message) {
    StringBuilder result = new StringBuilder();
    message = message.toUpperCase();

    int keyIndex = 0;
    for (int i = 0; i < message.length(); i++) {
      char ch = message.charAt(i);

      if (ch >= 'A' && ch <= 'Z') {
        char k = key.charAt(keyIndex % key.length());
        int shift = k - 'A';
        char encrypted = (char) ('A' + (ch - 'A' + shift) % 26);
        result.append(encrypted);
        keyIndex++;
      } else {
        result.append(ch); // Keep spaces and punctuation
      }
    }

    return result.toString();
  }
}
