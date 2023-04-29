package assignments.assignment3.user.menu;
import assignments.assignment3.user.Member;
import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.CuciService;
import assignments.assignment3.nota.service.SetrikaService;

public class MemberSystem extends SystemCLI {
    /**
     * Memproses pilihan dari Member yang masuk ke sistem ini sesuai dengan menu specific.
     *
     * @param choice -> pilihan pengguna.
     * @return true jika user log.
     */
    @Override
    protected boolean processChoice(int choice) {
        boolean logout = false;
        switch (choice) {
            case 1 -> processLaundryRequest();
            case 2 -> processDetailNota();
            case 3 -> logout = true;
            default -> System.out.println("Pilihan tidak valid, silakan coba lagi.\n");
        }
        return logout;
    }

    /**
     * Displays specific menu untuk Member biasa.
     */
    @Override
    protected void displaySpecificMenu() {
        System.out.println("1. Saya ingin laundry");
        System.out.println("2. Lihat detail nota saya");
        System.out.println("3. Logout");
    }

    /**
     * Menambahkan Member baru ke sistem.
     *
     * @param member -> Member baru yang akan ditambahkan.
     */
    public void addMember(Member member) {
        Member[] tempMemberList = new Member[memberList.length + 1];

        for (int i = 0; i < memberList.length; i++) {
            tempMemberList[i] = memberList[i];
        }

        memberList = tempMemberList;
        tempMemberList[memberList.length - 1] = member;
    }
    
    protected void processLaundryRequest() {
        System.out.println("Masukan paket laundry:");                 // Mencetak paket yang tersedia
        System.out.println("+-------------Paket-------------+");
        System.out.println("| Express | 1 Hari | 12000 / Kg |");
        System.out.println("| Fast    | 2 Hari | 10000 / Kg |");
        System.out.println("| Reguler | 3 Hari |  7000 / Kg |");
        System.out.println("+-------------------------------+");
        String paketPilihan = in.nextLine();                            // Input pilihan paket

        System.out.println("Masukan berat cucian anda [Kg]:");
        int berat = in.nextInt();                                       // Input berat cucian
        if (berat > 0 && berat < 2) {                                   // Jika berat cucian kurang dari 2 kg, maka akan diset menjadi 2 kg
            berat = 2;
            System.out.println("Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg");
        }

        Nota nota = new Nota(loginMember, berat, paketPilihan, NotaManager.fmt.format(NotaManager.cal.getTime()));  // Instansiasi objek nota
        nota.addService(new CuciService());                             // Menambahkan service cuci ke nota

        System.out.println("Apakah kamu ingin cucianmu disetrika oleh staff professional kami?");
        System.out.println("Hanya tambah 1000 / kg :0");
        System.out.print("[Ketik x untuk tidak mau]: ");
        String setrikaService = in.next();
        if (!setrikaService.equalsIgnoreCase("x")) {      // Jika input selain x, maka akan menambahkan service setrika ke nota
            nota.addService(new SetrikaService());
        }

        System.out.println("Mau diantar oleh kurir kami? Dijamin aman dan cepat sampai tujuan!");
        System.out.println("Cuma 2000 / 4kg, kemudian 500 / kg");
        System.out.print("[Ketik x untuk tidak mau]: ");
        String antarService = in.next();
        if (!antarService.equalsIgnoreCase("x")); {       // Jika input selain x, maka akan menambahkan service antar ke nota
            nota.addService(new AntarService());    
        }

        System.out.println("Nota berhasil dibuat!\n");
        NotaManager.addNota(nota);                                      // Menambahkan nota ke class NotaManager dan Member itu sendiri
        loginMember.addNota(nota);
    }

    protected void processDetailNota() {
        for (Nota nota : loginMember.getNotaList()) {                   // Iterasi semua nota yang ada di list nota
            System.out.println(nota.toString());                        // Mencetak semua nota berserta servicenya sesuai format
        }
    }
}