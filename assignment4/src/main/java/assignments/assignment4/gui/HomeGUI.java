package assignments.assignment4.gui;

import assignments.assignment3.nota.NotaManager;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;

import static assignments.assignment3.nota.NotaManager.toNextDay;

public class HomeGUI extends JPanel {
    public static final String KEY = "HOME";
    private JLabel titleLabel;
    private JLabel dateLabel;
    private JPanel mainPanel;
    private JButton loginButton;
    private JButton registerButton;
    private JButton toNextDayButton;

    public HomeGUI() {
        // Setup layout    
        super(new BorderLayout());                                 

        // Set up main panel
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initGUI();

        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Method untuk menginisialisasi GUI.
     * Pada page ini terdapat label untuk title dan tanggal hari ini
     * serta terdapat tiga button yaitu login, register, dan next day
     **/
    private void initGUI() {
        // Label title berupa pesan selamat datang
        titleLabel = new JLabel("Selamat datang di CuciCuci System!");      
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));          

        GridBagConstraints constraints = new GridBagConstraints();               
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 0, 5, 0);
        mainPanel.add(titleLabel, constraints);

        // Button Login, Register, dan Next Day beserta action listenernya
        loginButton = new JButton("Login");
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.NONE;
        loginButton.setPreferredSize(new Dimension(100, 30));
        loginButton.addActionListener(e -> handleToLogin());
        mainPanel.add(loginButton, constraints);

        registerButton = new JButton("Register");
        constraints.gridy = 2;
        registerButton.setPreferredSize(new Dimension(100, 30));
        registerButton.addActionListener(e -> handleToRegister());
        mainPanel.add(registerButton, constraints);

        toNextDayButton = new JButton("Next Day");
        constraints.gridy = 3;
        toNextDayButton.setPreferredSize(new Dimension(100, 30));
        toNextDayButton.addActionListener(e -> handleNextDay());
        mainPanel.add(toNextDayButton, constraints);

        // Label untuk menampilkan tanggal hari ini
        dateLabel = new JLabel("Hari ini: " + NotaManager.fmt.format(NotaManager.cal.getTime()));
        dateLabel.setHorizontalAlignment(SwingConstants.CENTER);

        constraints.gridy = 4;
        constraints.anchor = GridBagConstraints.PAGE_END;
        mainPanel.add(dateLabel, constraints);
    }

    /**
     * Method untuk pergi ke halaman register.
     * Akan dipanggil jika pengguna menekan "registerButton"
     **/
    private static void handleToRegister() {
        MainFrame.getInstance().navigateTo(RegisterGUI.KEY);
    }

    /**
     * Method untuk pergi ke halaman login.
     * Akan dipanggil jika pengguna menekan "loginButton"
     **/
    private static void handleToLogin() {
        MainFrame.getInstance().navigateTo(LoginGUI.KEY);
    }

    /**
     * Method untuk skip hari.
     * Akan dipanggil jika pengguna menekan "toNextDayButton"
     **/
    private void handleNextDay() {
        toNextDay();
        dateLabel.setText("Hari ini: " + NotaManager.fmt.format(NotaManager.cal.getTime()));
        JOptionPane.showMessageDialog(null, "Kamu tidur hari ini... zzz...", "Day Changed", JOptionPane.INFORMATION_MESSAGE);
    }
}   