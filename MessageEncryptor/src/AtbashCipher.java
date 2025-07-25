public class AtbashCipher implements Cipher {
  public String encrypt(String message) {
    StringBuilder result = new StringBuilder();
    message = message.toUpperCase();

    for (char ch : message.toCharArray()) {
      if (ch >= 'A' && ch <= 'Z') {
        char mirror = (char) ('Z' - (ch - 'A'));
        result.append(mirror);
      } else {
        result.append(ch);
      }
    }

    return result.toString();
  }
}
