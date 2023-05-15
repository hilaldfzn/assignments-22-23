package assignments.assignment4.gui.member.member;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.user.Member;
import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.MainFrame;
import assignments.assignment4.gui.member.AbstractMemberGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MemberSystemGUI extends AbstractMemberGUI {
    public static final String KEY = "MEMBER";

    public MemberSystemGUI(SystemCLI systemCLI) {
        super(systemCLI);
    }

    @Override
    public String getPageName() {
        return KEY;
    }

    public Member getLoggedInMember() {
        return loggedInMember;
    }

    /**
     * Method ini mensupply buttons yang sesuai dengan requirements MemberSystem.
     * Button yang disediakan method ini BELUM memiliki ActionListener.
     *
     * @return Array of JButton, berisi button yang sudah stylize namun belum ada ActionListener.
     * */
    @Override
    protected JButton[] createButtons() {
        // TODO
        JButton createNotaButton = new JButton("Saya ingin laundry");
        JButton showDetailNotaButton = new JButton("Lihat detail nota saya");

        return new JButton[]{
            createNotaButton,
            showDetailNotaButton
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
            e -> createNota(),
            e -> showDetailNota(),
        };
    }

    /**
     * Menampilkan detail Nota milik loggedInMember.
     * Akan dipanggil jika pengguna menekan button pertama pada createButtons
     * */
    private void showDetailNota() {
        // TODO
        if (loggedInMember.getNotaList().length != 0) {
            StringBuilder sb = new StringBuilder();
            for (Nota nota : loggedInMember.getNotaList()) {
                sb.append(String.format("%s%n", nota.toString()));
            }

            JTextArea textArea = new JTextArea(sb.toString());
            textArea.setFont(new Font("monospaced", Font.PLAIN, 12));
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setEditable(false);

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(500, 300));
            JOptionPane.showMessageDialog(null, scrollPane, "Detail Nota", JOptionPane.INFORMATION_MESSAGE);;
        } else {
            JOptionPane.showMessageDialog(null, "Member belum memiliki nota!", "Nota Not Found", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Pergi ke halaman CreateNotaGUI.
     * Akan dipanggil jika pengguna menekan button kedua pada createButtons
     * */
    private void createNota() {
        // TODO
        MainFrame.getInstance().navigateTo(CreateNotaGUI.KEY);
    }
}