package assignments.assignment3.nota;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NotaManager {
    public static SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
    public static Calendar cal = Calendar.getInstance();
    static public Nota[] notaList = new Nota[0];

    /**
     * Skips ke hari berikutnya dan update semua entri nota yang sesuai.
     */
    public static void toNextDay() {
        // implement skip hari
        cal.add(Calendar.DATE, 1);

        for (Nota nota : notaList) {
            nota.toNextDay();
        }
    }

    /**
     * Menambahkan nota baru ke NotaList.
     *
     * @param nota Nota object untuk ditambahkan.
     */
    public static void addNota(Nota nota) {
        // implement add nota
        Nota[] tempNotaList = new Nota[notaList.length + 1];

        for (int i = 0; i < notaList.length; i++) {
            tempNotaList[i] = notaList[i];
        }

        notaList = tempNotaList;
        tempNotaList[notaList.length - 1] = nota;
    }
}