package assignments.assignment4.gui.member.member;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.CuciService;
import assignments.assignment3.nota.service.SetrikaService;
import assignments.assignment3.user.Member;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateNotaGUI extends JPanel {
    public static final String KEY = "CREATE_NOTA";
    private JLabel paketLabel;
    private JComboBox<String> paketComboBox;
    private JButton showPaketButton;
    private JLabel beratLabel;
    private JTextField beratTextField;
    private JCheckBox setrikaCheckBox;
    private JCheckBox antarCheckBox;
    private JButton createNotaButton;
    private JButton backButton;
    private final SimpleDateFormat fmt;
    private final Calendar cal;
    private final MemberSystemGUI memberSystemGUI;

    public CreateNotaGUI(MemberSystemGUI memberSystemGUI) {
        this.memberSystemGUI = memberSystemGUI;
        this.fmt = NotaManager.fmt;
        this.cal = NotaManager.cal;

        // Set up main panel
        initGUI();
    }

    /**
     * Method untuk menginisialisasi GUI.
     **/
    private void initGUI() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
        JPanel mainPanel = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);
    
        Font labelFont = new Font("Arial", Font.BOLD, 12);
        Font buttonFont = new Font("Arial", Font.BOLD, 12);
    
        // Label dan ComboBox untuk memilih paket
        paketLabel = new JLabel("Paket Laundry");
        paketLabel.setFont(labelFont);
        constraints.gridx = 0;
        constraints.gridy = 0;
        mainPanel.add(paketLabel, constraints);
    
        String[] paketOptions = {"Express", "Fast", "Reguler"};
        paketComboBox = new JComboBox<>(paketOptions);
        paketComboBox.setPreferredSize(new Dimension(75, 25));
        paketComboBox.setFont(buttonFont);
        constraints.gridx = 1;
        constraints.gridy = 0;
        mainPanel.add(paketComboBox, constraints);
    
        // Button untuk menampilkan informasi paket yang tersedia
        showPaketButton = new JButton("Show Paket");
        showPaketButton.setPreferredSize(new Dimension(110, 25));
        showPaketButton.setFont(buttonFont);
        showPaketButton.addActionListener(e -> showPaket());
        constraints.gridx = 2;
        constraints.gridy = 0;
        mainPanel.add(showPaketButton, constraints);
    
        // Label dan TextField untuk berat
        beratLabel = new JLabel("Berat Cucian (Kg)");
        beratLabel.setFont(labelFont);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        mainPanel.add(beratLabel, constraints);
    
        beratTextField = new JTextField();
        beratTextField.setPreferredSize(new Dimension(75, 25));
        constraints.gridx = 1;
        constraints.gridy = 2;
        mainPanel.add(beratTextField, constraints);
    
        // Checkbox untuk setrika service
        setrikaCheckBox = new JCheckBox("Tambah Setrika Service (1000 / kg)");
        setrikaCheckBox.setFont(labelFont);
        constraints.gridx = 0;
        constraints.gridy = 3;
        mainPanel.add(setrikaCheckBox, constraints);
    
        // Checkbox untuk antar service
        antarCheckBox = new JCheckBox("Tambah Antar Service (2000 / 4kg pertama, kemudian 500 / kg)");
        antarCheckBox.setFont(labelFont);
        constraints.gridx = 0;
        constraints.gridy = 4;
        mainPanel.add(antarCheckBox, constraints);
    
        // Button untuk membuat nota
        createNotaButton = new JButton("Buat Nota");
        createNotaButton.setFont(buttonFont);
        createNotaButton.addActionListener(e -> createNota());
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 3;
        mainPanel.add(createNotaButton, constraints);
    
        // Button untuk kembali
        backButton = new JButton("Kembali");
        backButton.setFont(buttonFont);
        backButton.addActionListener(e -> handleBack());
        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 3;
        mainPanel.add(backButton, constraints);
    
        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Menampilkan list paket pada user.
     * Akan dipanggil jika pengguna menekan "showPaketButton"
     **/
    private void showPaket() {
        String paketInfo = """
                        <html><pre>
                        +-------------Paket-------------+
                        | Express | 1 Hari | 12000 / Kg |
                        | Fast    | 2 Hari | 10000 / Kg |
                        | Reguler | 3 Hari |  7000 / Kg |
                        +-------------------------------+
                        </pre></html>
                        """;
        JLabel label = new JLabel(paketInfo);
        label.setFont(new Font("monospaced", Font.PLAIN, 12));
        JOptionPane.showMessageDialog(this, label, "Paket Information", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Method untuk melakukan pengecekan input user dan mendaftarkan nota yang sudah valid pada sistem.
     * Akan dipanggil jika pengguna menekan "createNotaButton"
     **/
    private void createNota() {
        String paket = paketComboBox.getSelectedItem().toString();
        int berat = 0;
        try {
            berat = Integer.parseInt(beratTextField.getText());
            if (berat > 0 && berat < 2) {
                berat = 2;
                JOptionPane.showMessageDialog(null, "Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg", 
                                              "Info", JOptionPane.INFORMATION_MESSAGE);
            } else if (berat < 1) {
                JOptionPane.showMessageDialog(null, "Berat cucian harus dalam bilangan positif", "Error", JOptionPane.ERROR_MESSAGE);
                beratTextField.setText("");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Berat cucian harus berisi angka", "Error", JOptionPane.ERROR_MESSAGE);
            beratTextField.setText("");
            return;
        }

        Member loggedInMember = memberSystemGUI.getLoggedInMember();
        Nota nota = new Nota(loggedInMember, berat, paket, fmt.format(cal.getTime()));
        nota.addService(new CuciService());

        if (setrikaCheckBox.isSelected()) {
            nota.addService(new SetrikaService());
        }

        if (antarCheckBox.isSelected()) {
            nota.addService(new AntarService());
        }

        // Menampilkan message "Nota berhasil dibuat!", nota akan ditambahkan ke NotaManager dan notalist milik member
        JOptionPane.showMessageDialog(null, "Nota berhasil dibuat!", "Success Create Nota", JOptionPane.INFORMATION_MESSAGE);
        NotaManager.addNota(nota);
        loggedInMember.addNota(nota);
        clearFields();
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     **/
    private void handleBack() {
        clearFields();
        MainFrame.getInstance().navigateTo(MemberSystemGUI.KEY);
    }

    /**
     * Method untuk mengosongkan semua input field
     **/
    private void clearFields() {
        paketComboBox.setSelectedIndex(0);
        beratTextField.setText("");
        setrikaCheckBox.setSelected(false);
        antarCheckBox.setSelected(false);
    }
}   