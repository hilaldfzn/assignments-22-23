package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment3.user.Member;
import assignments.assignment3.user.menu.MemberSystem;
import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.MainFrame;
import assignments.assignment4.gui.member.employee.EmployeeSystemGUI;
import assignments.assignment4.gui.member.member.MemberSystemGUI;

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
        idLabel = new JLabel("ID");
        idTextField = new JTextField(20);

        passwordLabel = new JLabel("Password");
        passwordField = new JPasswordField(20);

        loginButton = new JButton("Login");
        loginButton.addActionListener(e -> handleLogin());

        backButton = new JButton("Kembali");
        backButton.addActionListener(e -> handleBack());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 0, 5, 0);
        mainPanel.add(idLabel, constraints);

        constraints.gridx = 1;
        mainPanel.add(idTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        mainPanel.add(passwordLabel, constraints);

        constraints.gridx = 1;
        mainPanel.add(passwordField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        mainPanel.add(loginButton, constraints);

        constraints.gridy = 3;
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
     * Method untuk login pada sistem.
     * Akan dipanggil jika pengguna menekan "loginButton"
     * */
    private void handleLogin() {
        // TODO
        String id = idTextField.getText();
        String password = new String(passwordField.getPassword());

        if (id.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Field ID dan Password wajib diisi!", "Empty Field", JOptionPane.ERROR_MESSAGE);
        } else {
            SystemCLI userSystem = loginManager.getSystem(id);

            if (userSystem == null) {
                JOptionPane.showMessageDialog(null, "Invalid ID or Password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
            } else {
                Member userLogin = userSystem.authUser(id, password);

                if (userLogin == null) {
                    JOptionPane.showMessageDialog(null, "Invalid ID or Password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (userSystem instanceof MemberSystem) {
                        MainFrame.getInstance().navigateTo(MemberSystemGUI.KEY);
                    } else {
                        MainFrame.getInstance().navigateTo(EmployeeSystemGUI.KEY);
                    }
                    MainFrame.getInstance().login(id, password);
                }
            }
        }
        clearFields();
    }

    private void clearFields() {
        idTextField.setText("");
        passwordField.setText("");
    }
}