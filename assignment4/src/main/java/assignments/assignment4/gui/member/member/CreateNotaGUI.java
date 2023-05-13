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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        // Set up main panel, Feel free to make any changes
        initGUI();
    }

    /**
     * Method untuk menginisialisasi GUI.
     * Selama funsionalitas sesuai dengan soal, tidak apa apa tidak 100% sama.
     * Be creative and have fun!
     * */
    private void initGUI() {
        // TODO
        setLayout(new GridLayout(6, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        paketLabel = new JLabel("Paket Laundry:");
        add(paketLabel);

        paketComboBox = new JComboBox<>(new String[] {"Express", "Fast", "Reguler"});
        add(paketComboBox);
        add(new JLabel());

        showPaketButton = new JButton("Show Paket");
        showPaketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPaket();
            }
        });
        add(showPaketButton);

        beratLabel = new JLabel("Berat Cucian (Kg):");
        add(beratLabel);
        beratTextField = new JTextField();
        add(beratTextField);

        setrikaCheckBox = new JCheckBox("Tambah Setrika Service (1000/kg)");
        add(setrikaCheckBox);
        add(new JLabel());

        antarCheckBox = new JCheckBox("Tambah Antar Service (2000/4kg pertama, kemudian 500/kg)");
        add(antarCheckBox);
        add(new JLabel());

        createNotaButton = new JButton("Buat Nota");
        add(createNotaButton);
        createNotaButton.addActionListener(e -> createNota());

        backButton = new JButton("Kembali");
        add(backButton);
        backButton.addActionListener(e -> handleBack());
    }

    /**
     * Menampilkan list paket pada user.
     * Akan dipanggil jika pengguna menekan "showPaketButton"
     * */
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
     * */
    private void createNota() {
        // TODO
        String paket = (String) paketComboBox.getSelectedItem();
        int berat = 0;
        try {
            berat = Integer.parseInt(beratTextField.getText());
            if (berat > 0 && berat < 2) {
                berat = 2;
                JOptionPane.showMessageDialog(this, "Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg", 
                                              "Info", JOptionPane.ERROR_MESSAGE);
            } 
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Berat cucian harus berisi angka", "Error", JOptionPane.ERROR_MESSAGE);
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

        JOptionPane.showMessageDialog(this, "Nota berhasil dibuat!", "Success Create Nota", JOptionPane.INFORMATION_MESSAGE);
        NotaManager.addNota(nota);
        //loggedInMember.addNota(nota);
        clearFields();
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        // TODO
        clearFields();
        MainFrame.getInstance().navigateTo(MemberSystemGUI.KEY);
    }

    /**
     * Method to clear all input fields.
     */
    private void clearFields() {
        paketComboBox.setSelectedIndex(0);
        beratTextField.setText("");
        setrikaCheckBox.setSelected(false);
        antarCheckBox.setSelected(false);
    }
}