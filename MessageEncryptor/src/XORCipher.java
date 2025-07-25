// src/XORCipher.java
public class XORCipher implements Cipher {
  private final char key;

  public XORCipher(char key) {
    this.key = key;
  }

  public String encrypt(String message) {
    StringBuilder encrypted = new StringBuilder();

    for (char ch : message.toCharArray()) {
      encrypted.append((char) (ch ^ key));
    }

    return encrypted.toString();
  }
}
