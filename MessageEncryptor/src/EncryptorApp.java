import java.util.Scanner;

public class EncryptorApp {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    System.out.println("Enter your message:");
    String message = sc.nextLine();

    System.out.println("Choose encryption type:");
    System.out.println("1. Caesar Cipher");
    System.out.println("2. XOR Cipher");
    int choice = sc.nextInt();
    sc.nextLine(); // consume newline

    Cipher cipher;

    if (choice == 1) {
      cipher = new CaesarCipher();
    } else if (choice == 2) {
      System.out.print("Enter a single character as XOR key: ");
      char xorKey = sc.nextLine().charAt(0);
      cipher = new XORCipher(xorKey);
    } else {
      System.out.println("Invalid choice.");
      return;
    }

    String encrypted = cipher.encrypt(message);
    System.out.println("Encrypted Message: " + encrypted);
  }
}
