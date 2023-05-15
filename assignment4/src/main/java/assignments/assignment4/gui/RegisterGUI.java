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

    public RegisterGUI(LoginManager loginManager) {
        super(new BorderLayout()); // Setup layout, Feel free to make any changes
        this.loginManager = loginManager;

        // Set up main panel, Feel free to make any changes
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initGUI();

        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Method untuk menginisialisasi GUI.
     * Selama funsionalitas sesuai dengan soal, tidak apa apa tidak 100% sama.
     * Be creative and have fun!
     * */
    private void initGUI() {
        // TODO
        nameLabel = new JLabel("Nama");
        nameTextField = new JTextField(20);

        phoneLabel = new JLabel("Nomor HP");
        phoneTextField = new JTextField(20);

        passwordLabel = new JLabel("Password");
        passwordField = new JPasswordField(20);

        registerButton = new JButton("Register");
        registerButton.addActionListener(e -> handleRegister());

        backButton = new JButton("Kembali");
        backButton.addActionListener(e -> handleBack());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 0, 5, 0);
        mainPanel.add(nameLabel, constraints);
    
        constraints.gridx = 1;
        mainPanel.add(nameTextField, constraints);
    
        constraints.gridx = 0;
        constraints.gridy = 1;
        mainPanel.add(phoneLabel, constraints);
    
        constraints.gridx = 1;
        mainPanel.add(phoneTextField, constraints);
    
        constraints.gridx = 0;
        constraints.gridy = 2;
        mainPanel.add(passwordLabel, constraints);
    
        constraints.gridx = 1;
        mainPanel.add(passwordField, constraints);
    
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        mainPanel.add(registerButton, constraints);
    
        constraints.gridy = 4;
        mainPanel.add(backButton, constraints);
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        MainFrame.getInstance().navigateTo(HomeGUI.KEY);
        clearFields();
    }

    /**
    * Method untuk mendaftarkan member pada sistem.
    * Akan dipanggil jika pengguna menekan "registerButton"
    * */
    private void handleRegister() {
        // TODO
        String name = nameTextField.getText().trim();
        String phone = phoneTextField.getText();
        String password = new String(passwordField.getPassword());

        if (name.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Semua field di atas wajib diisi!",
                                          "Empty Field", JOptionPane.ERROR_MESSAGE);
        } else {
            //String id = NotaGenerator.generateId(name, phone);
            Member member = loginManager.register(name, phone, password);

            if (!isNumeric(phone)) {
                JOptionPane.showMessageDialog(null, "Nomor handphone harus berisi angka!", 
                                            "Invalid Phone Number", JOptionPane.ERROR_MESSAGE);
                phoneTextField.setText("");
            } else {
                if (member != null) {
                    JOptionPane.showMessageDialog(null, "Berhasil membuat user dengan ID " + member.getId() + "!", 
                                                "Registration Successful", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "User dengan nama " + name + " dan nomor hp " + phone + " sudah ada!", 
                                                "Registration Failed", JOptionPane.ERROR_MESSAGE);
                }
                clearFields();
                MainFrame.getInstance().navigateTo(HomeGUI.KEY);
            }
        }
    }

    private void clearFields() {
        nameTextField.setText("");
        phoneTextField.setText("");
        passwordField.setText("");
    }

    private boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("^[0-9]+$");
        return pattern.matcher(str).matches();
    }
}