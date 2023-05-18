package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment3.user.Member;
import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;

public class LoginGUI extends JPanel {
    public static final String KEY = "LOGIN";
    private JPanel mainPanel;
    private JLabel idLabel;
    private JTextField idTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton backButton;
    private LoginManager loginManager;

    public LoginGUI(LoginManager loginManager) {
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
     * Pada page ini terdapat dua field yang harus diisi dan dua button
     * Field: ID dan Password
     * Button: Kembali dan Login
     **/
    private void initGUI() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 5, 5, 5);

        // Label dan text field untuk ID
        idLabel = new JLabel("ID");
        constraints.gridx = 0;
        constraints.gridy = 0;
        mainPanel.add(idLabel, constraints);

        idTextField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 0;
        mainPanel.add(idTextField, constraints);

        // Label dan password untuk Password
        passwordLabel = new JLabel("Password");
        constraints.gridx = 0;
        constraints.gridy = 1;
        mainPanel.add(passwordLabel, constraints);

        passwordField = new JPasswordField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        mainPanel.add(passwordField, constraints);

        // Button Login dan Kembali beserta action listenernya
        loginButton = new JButton("Login");
        loginButton.addActionListener(e -> handleLogin());
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        mainPanel.add(loginButton, constraints);

        backButton = new JButton("Kembali");
        backButton.addActionListener(e -> handleBack());
        constraints.gridx = 0;
        constraints.gridy = 3;
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
     * Method untuk login pada sistem.
     * Akan dipanggil jika pengguna menekan "loginButton"
     **/
    private void handleLogin() {
        String id = idTextField.getText();
        String password = new String(passwordField.getPassword());

        // Akan menampilkan error message ketika masih ada field yang kosong
        if (id.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Field ID dan Password wajib diisi!", "Empty Field", JOptionPane.ERROR_MESSAGE);
        } else {
            SystemCLI userSystem = loginManager.getSystem(id);          // Membuat objek userSystem
            
            // Jika input ID salah, tidak akan ada objek userSystem (null)
            if (userSystem == null) {
                JOptionPane.showMessageDialog(null, "Invalid ID or Password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            } else {
                Member userLogin = userSystem.authUser(id, password);   // Membuat objek userLogin

                if (userLogin == null) {
                    JOptionPane.showMessageDialog(null, "Invalid ID or Password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                } else {
                    MainFrame.getInstance().login(id, password);        // User akan login dan ditujukan ke page member system atau employee system
                }
            }
        }
        clearFields();                                                  // Mengosongkan semua field setelah melakukan login
    }

    /**
     * Method untuk mengosongkan input field
     **/
    private void clearFields() {
        idTextField.setText("");
        passwordField.setText("");
    }
}   