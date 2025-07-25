import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class EncryptorUI {
  public static void main(String[] args) {
    JFrame frame = new JFrame("File Encryption Tool");
    frame.setSize(550, 400);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new GridBagLayout());

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(6, 6, 6, 6);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // File Selector
    JLabel fileTextLabel = new JLabel("1. Select your .txt file:");
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 1;
    frame.add(fileTextLabel, gbc);

    JButton fileButton = new JButton("Browse...");
    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.gridwidth = 1;
    frame.add(fileButton, gbc);

    JLabel fileLabel = new JLabel("No file selected.");
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.gridwidth = 2;
    frame.add(fileLabel, gbc);

    // Cipher Dropdown
    JLabel cipherLabel = new JLabel("2. Choose encryption method:");
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.gridwidth = 2;
    frame.add(cipherLabel, gbc);

    JComboBox<String> cipherDropdown = new JComboBox<>(new String[] {
        "Caesar Cipher",
        "XOR Cipher",
        "Atbash Cipher",
        "ROT13 Cipher",
        "Vigenere Cipher"
    });
    cipherDropdown.setPreferredSize(new Dimension(150, 25));
    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.gridwidth = 2;
    frame.add(cipherDropdown, gbc);

    // Key Input
    JLabel keyLabel = new JLabel("3. Enter key (if required):");
    gbc.gridx = 0;
    gbc.gridy = 4;
    gbc.gridwidth = 2;
    frame.add(keyLabel, gbc);

    JTextField keyInput = new JTextField();
    keyInput.setPreferredSize(new Dimension(150, 25));
    keyInput.setToolTipText("1 char for XOR, word for Vigenere");
    keyInput.setEnabled(false);
    gbc.gridx = 0;
    gbc.gridy = 5;
    gbc.gridwidth = 2;
    frame.add(keyInput, gbc);

    // Encrypt Button
    JButton encryptButton = new JButton("Encrypt File");
    gbc.gridx = 0;
    gbc.gridy = 6;
    gbc.gridwidth = 2;
    frame.add(encryptButton, gbc);

    // Tutorial Area
    JTextArea tutorial = new JTextArea(
        "How it works:\n" +
            "1. Select a .txt file using the Browse button.\n" +
            "2. Choose an encryption method from the list.\n" +
            "3. If using XOR or Vigenère, enter a key in the box.\n" +
            "4. Click 'Encrypt File' to save <filename>Encrypted.txt.");
    tutorial.setEditable(false);
    tutorial.setLineWrap(true);
    tutorial.setWrapStyleWord(true);
    tutorial.setBackground(frame.getBackground());
    tutorial.setFont(new Font("SansSerif", Font.PLAIN, 12));
    JScrollPane scrollPane = new JScrollPane(tutorial);
    scrollPane.setBorder(BorderFactory.createTitledBorder("How It Works"));

    gbc.gridx = 0;
    gbc.gridy = 7;
    gbc.gridwidth = 2;
    frame.add(scrollPane, gbc);

    File[] selectedFile = new File[1];

    // Enable key input conditionally
    cipherDropdown.addActionListener(e -> {
      String selected = (String) cipherDropdown.getSelectedItem();
      keyInput.setEnabled("XOR Cipher".equals(selected) || "Vigenere Cipher".equals(selected));
    });

    // File picker
    fileButton.addActionListener(e -> {
      JFileChooser chooser = new JFileChooser();
      chooser.setDialogTitle("Choose a .txt file");
      int result = chooser.showOpenDialog(null);
      if (result == JFileChooser.APPROVE_OPTION) {
        File file = chooser.getSelectedFile();
        if (file.getName().endsWith(".txt")) {
          selectedFile[0] = file;
          fileLabel.setText("Selected: " + file.getName());
        } else {
          JOptionPane.showMessageDialog(frame, "Please choose a .txt file.");
        }
      }
    });

    // Encrypt and save file
    encryptButton.addActionListener(e -> {
      if (selectedFile[0] == null) {
        JOptionPane.showMessageDialog(frame, "Please select a .txt file first.");
        return;
      }

      String selectedCipher = (String) cipherDropdown.getSelectedItem();
      String key = keyInput.getText();
      Cipher cipher;

      try {
        switch (selectedCipher) {
          case "Caesar Cipher":
            cipher = new CaesarCipher();
            break;
          case "XOR Cipher":
            if (key.length() != 1) {
              JOptionPane.showMessageDialog(frame, "XOR key must be one character.");
              return;
            }
            cipher = new XORCipher(key.charAt(0));
            break;
          case "Atbash Cipher":
            cipher = new AtbashCipher();
            break;
          case "ROT13 Cipher":
            cipher = new ROT13Cipher();
            break;
          case "Vigenere Cipher":
            if (key.length() < 1) {
              JOptionPane.showMessageDialog(frame, "Vigenere key must be at least 1 character.");
              return;
            }
            cipher = new VigenereCipher(key);
            break;
          default:
            JOptionPane.showMessageDialog(frame, "Unknown cipher.");
            return;
        }

        // Read file content
        BufferedReader reader = new BufferedReader(new FileReader(selectedFile[0]));
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
          content.append(line).append("\n");
        }
        reader.close();

        // Encrypt
        String encrypted = cipher.encrypt(content.toString());

        // Save to file
        String originalPath = selectedFile[0].getAbsolutePath();
        String newPath = originalPath.replace(".txt", "Encrypted.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(newPath));
        writer.write(encrypted);
        writer.close();

        JOptionPane.showMessageDialog(frame, "File encrypted and saved as:\n" + newPath);

      } catch (IOException ex) {
        JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
      }
    });

    frame.setVisible(true);
  }
}
