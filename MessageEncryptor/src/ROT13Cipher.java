public class ROT13Cipher implements Cipher {
  public String encrypt(String message) {
    StringBuilder result = new StringBuilder();
    message = message.toUpperCase();

    for (char ch : message.toCharArray()) {
      if (ch >= 'A' && ch <= 'Z') {
        char shifted = (char) ('A' + (ch - 'A' + 13) % 26);
        result.append(shifted);
      } else {
        result.append(ch);
      }
    }

    return result.toString();
  }
}
