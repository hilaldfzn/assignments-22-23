package assignments.assignment4.gui.member.employee;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;

import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.gui.member.AbstractMemberGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class EmployeeSystemGUI extends AbstractMemberGUI {
    public static final String KEY = "EMPLOYEE";

    public EmployeeSystemGUI(SystemCLI systemCLI) {
        super(systemCLI);
    }

    @Override
    public String getPageName(){
        return KEY;
    }

    /**
     * Method ini mensupply buttons yang sesuai dengan requirements Employee.
     * Button yang disediakan method ini BELUM memiliki ActionListener.
     *
     * @return Array of JButton, berisi button yang sudah stylize namun belum ada ActionListener.
     * */
    @Override
    protected JButton[] createButtons() {
        // TODO
        JButton cuciButton = new JButton("It's nyuci time");
        JButton displayNotaButton = new JButton("Display List Nota");

        return new JButton[]{
            cuciButton,
            displayNotaButton
        };
    }

    /**
     * Method ini mensupply ActionListener korespondensi dengan button yang dibuat createButtons()
     * sesuai dengan requirements MemberSystem.
     *
     * @return Array of ActionListener.
     * */
    @Override
    protected ActionListener[] createActionListeners() {
        return new ActionListener[]{
                e -> cuci(),
                e -> displayNota(),
        };
    }

    /**
     * Menampilkan semua Nota yang ada pada sistem.
     * Akan dipanggil jika pengguna menekan button pertama pada createButtons
     * */
    private void displayNota() {
        // TODO
        if (NotaManager.notaList.length != 0) {    
            StringBuilder sb = new StringBuilder();
            for (Nota nota : NotaManager.notaList) {
                sb.append(String.format("Nota %d : %s\n", nota.getId(), nota.getNotaStatus()));
            }
            JTextArea textArea = new JTextArea(sb.toString());
            textArea.setFont(new Font("monospaced", Font.PLAIN, 12));
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setEditable(false);

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 300));
            JOptionPane.showMessageDialog(this, scrollPane, "List Nota", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Belum ada nota dalam sistem!", "Nota Not Found", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Menampilkan dan melakukan action mencuci.
     * Akan dipanggil jika pengguna menekan button kedua pada createButtons
     * */
    private void cuci() {
        // TODO
        if (NotaManager.notaList.length != 0) {
            for (Nota nota : NotaManager.notaList) {
                JOptionPane.showMessageDialog(null, "Stand back! " + loggedInMember.getNama() + " beginning to nyuci!",
                                          "Nota Status", JOptionPane.INFORMATION_MESSAGE);

                String kerjakan = String.format("<html><pre> Nota %d : %s </pre></html>", nota.getId(), nota.kerjakan());
                nota.setNotaStatus();

                JLabel label = new JLabel(kerjakan);
                label.setFont(new Font("monospaced", Font.PLAIN, 12));
                JOptionPane.showMessageDialog(this, label, "Service", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Belum ada nota dalam sistem!", "Nota Not Found", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}