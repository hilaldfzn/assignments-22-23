package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment3.user.Member;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;

import java.util.regex.Pattern;

public class RegisterGUI extends JPanel {
    public static final String KEY = "REGISTER";
    private JPanel mainPanel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel phoneLabel;
    private JTextField phoneTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton registerButton;
    private LoginManager loginManager;
    private JButton backButton;
    private JCheckBox showPasswordCheckBox;

    public RegisterGUI(LoginManager loginManager) {
        // Setup layout
        super(new BorderLayout());                      
        this.loginManager = loginManager;

        // Set up main panel
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initGUI();

        add(mainPanel, BorderLayout.CENTER);            
    }

    /**
     * Method untuk menginisialisasi GUI.
     * Pada page ini terdapat tiga field yang harus diisi dan dua button
     * Field: Nama, Nomor Handphone, dan Password
     * Button: Kembali dan Register
     **/
    private void initGUI() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 5, 5, 5);
        
        // Label dan text field untuk Nama
        nameLabel = new JLabel("Nama");  
        constraints.gridx = 0;
        constraints.gridy = 0;                                         
        mainPanel.add(nameLabel, constraints);                              

        nameTextField = new JTextField(20);
        constraints.gridx = 1;
        mainPanel.add(nameTextField, constraints);

        // Label dan text field untuk Nomor Handphone
        phoneLabel = new JLabel("Nomor Handphone");
        constraints.gridx = 0;
        constraints.gridy = 1;
        mainPanel.add(phoneLabel, constraints);

        phoneTextField = new JTextField(20);
        constraints.gridx = 1;
        mainPanel.add(phoneTextField, constraints);

        // Label dan password field untuk Password
        passwordLabel = new JLabel("Password");
        constraints.gridx = 0;
        constraints.gridy = 2;
        mainPanel.add(passwordLabel, constraints);

        passwordField = new JPasswordField(20);
        constraints.gridx = 1;
        mainPanel.add(passwordField, constraints);

        // Checkbox untuk menampilkan atau menyembunyikan password
        showPasswordCheckBox = new JCheckBox("Show Password");
        showPasswordCheckBox.setFont(new Font("Arial", Font.PLAIN, 10));
        showPasswordCheckBox.addActionListener(e -> {
            JCheckBox checkBox = (JCheckBox) e.getSource();
            passwordField.setEchoChar(checkBox.isSelected() ? '\u0000' : '\u2022');
        });
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.WEST;
        mainPanel.add(showPasswordCheckBox, constraints);

        // Button Register dan Kembali beserta action listenernya
        registerButton = new JButton("Register");
        registerButton.addActionListener(e -> handleRegister());
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        mainPanel.add(registerButton, constraints);

        backButton = new JButton("Kembali");
        backButton.addActionListener(e -> handleBack());
        constraints.gridy = 5;
        mainPanel.add(backButton, constraints);
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     **/
    private void handleBack() {
        MainFrame.getInstance().navigateTo(HomeGUI.KEY);
        clearFields();
    }

    /**
    * Method untuk mendaftarkan member pada sistem.
    * Akan dipanggil jika pengguna menekan "registerButton"
    **/
    private void handleRegister() {
        String name = nameTextField.getText().trim();
        String phone = phoneTextField.getText();
        String password = new String(passwordField.getPassword());

        // Menampilkan error message ketika ada field yang masih kosong
        if (name.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Semua field di atas wajib diisi!",
                                          "Empty Field", JOptionPane.ERROR_MESSAGE);
        } else {
            // Menampilkan error message ketika terdapat karakter selain angka pada nomor handphone
            if (!isNumeric(phone)) {
                JOptionPane.showMessageDialog(null, "Nomor handphone harus berisi angka!", 
                                            "Invalid Phone Number", JOptionPane.ERROR_MESSAGE);
                phoneTextField.setText("");                                   // Mengosongkan field nomor HP
            } else {
                Member member = loginManager.register(name, phone, password);   // Membuat objek member yang melakukan register

                if (member != null) {
                    JOptionPane.showMessageDialog(null, "Berhasil membuat user dengan ID " + member.getId() + "!", 
                                                "Registration Successful", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "User dengan nama " + name + " dan nomor hp " + phone + " sudah ada!", 
                                                "Registration Failed", JOptionPane.ERROR_MESSAGE);
                }
                clearFields();                                                  // Mengosongkan semua input field
                MainFrame.getInstance().navigateTo(HomeGUI.KEY);                // Kembali ke page HomeGUI
            }
        }
    }

    /**
     * Method untuk mengosongkan input field
     **/
    private void clearFields() {
        nameTextField.setText("");
        phoneTextField.setText("");
        passwordField.setText("");
        showPasswordCheckBox.setSelected(false);
        passwordField.setEchoChar('\u2022');
    }

    /**
     * Method untuk mengecek apakah suatu string hanya terdiri dari angka atau tidak
     * 
     * @param str string yang akan diperiksa
     * @return true jika string hanya terdiri dari angka, false jika tidak
     **/
    private boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("^[0-9]+$");
        return pattern.matcher(str).matches();
    }
}