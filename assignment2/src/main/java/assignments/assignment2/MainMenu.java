package assignments.assignment2;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import static assignments.assignment1.NotaGenerator.*;

public class MainMenu {
    // Implementasi visibility modifier yang sesuai dan inisialisasi variable yang akan digunakan
    private static final Scanner input = new Scanner(System.in);
    private static SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
    private static Calendar cal = Calendar.getInstance();
    private static ArrayList<Member> memberList = new ArrayList <>();
    private static ArrayList<Nota> notaList = new ArrayList <>();
    private static int counterIdNota = 0;

     /* Method main
     * Program akan terus berjalan hingga user memilih menu selesai
     * Program akan menampilkan “Perintah tidak diketahui” jika pengguna memasukkan perintah selain yang tersedia */
    public static void main(String[] args) {
        boolean isRunning = true;

        while (isRunning) {
            printMenu();
            System.out.print("Pilihan : ");
            String command = input.nextLine();
            System.out.println("================================");

            switch (command) {
                case "1" -> handleGenerateUser();
                case "2" -> handleGenerateNota();
                case "3" -> handleListNota();
                case "4" -> handleListUser();
                case "5" -> handleAmbilCucian();
                case "6" -> handleNextDay();
                case "0" -> isRunning = false;
                default -> System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
        }
        System.out.println("Terima kasih telah menggunakan NotaGenerator!");
    }

    /* Method untuk generate ID user
     */
    private static void handleGenerateUser() {
        System.out.println("Masukkan nama Anda: ");             // Input nama pelanggan
        String namaPelanggan = input.nextLine(); 
        
        // Menghandle apabila input nama berupa string kosong akan meminta input kembali
        while (true) {
            if (namaPelanggan == "") {
                System.out.println("Masukkan nama Anda: ");
                namaPelanggan = input.nextLine();
            } else {
                break;
            }
        }

        System.out.println("Masukkan nomor handphone Anda: ");  // Input nomor handphone
        String phoneNum = input.nextLine();

        /* Jika input string kosong dan bukan digit angka, maka akan
         * mencetak pesan dan kembali meminta input hingga sesuai
         */
        while (!isValidInput(phoneNum)) {
            System.out.println("Field nomor hp hanya menerima digit.");
            phoneNum = input.nextLine();
        }
        
        Member member = new Member(namaPelanggan, phoneNum);    // Membuat objek member

        // Jika ID member sudah ada dalam list member, maka akan menampilkan pesan error
        if (memberIdExists(member.getId())) {
            System.out.printf("Member dengan nama %s dan nomor hp %s sudah ada!\n", namaPelanggan, phoneNum);

        // Jika ID member belum ada, maka akan di tambahkan ke dalam list member
        } else {
            memberList.add(member);
            System.out.printf("Berhasil membuat member dengan ID %s!\n", member.getId());
        }
    }

    private static void handleGenerateNota() {
        // Inisiasi variable
        boolean isNumeric = false;
        int idxMember = 0, bonusCounter = 0;
        String beratCucian;

        System.out.println("Masukan ID member:");               
        String memberId = input.nextLine();                     // Meminta input ID member
        
        // Memasukkan indeks untuk ID member
        for (int i = 0; i < memberList.size(); i++) {
            if (memberIdExists(memberId)) {
                idxMember = i;
            }
        }

        // Jika ID belum ada di dalam list member, maka akan menampilkan pesan error
        if (!memberIdExists(memberId)) {
            System.out.printf("Member dengan ID %s tidak ditemukan!\n", memberId);
            return;
        }

        System.out.println("Masukan paket laundry:");           // Meminta input paket laundry
            String paketLaundry = input.nextLine();
            int lamaPengerjaan;
            
            while (true) {
                /* Program tidak akan mengeluarkan pesan error jika
                * input jenis paket yang dipilih sesuai dengan
                * express, fast, reguler */
                if (paketLaundry.toLowerCase().equalsIgnoreCase("express")) {
                    lamaPengerjaan = 1;
                    break;
                } else if (paketLaundry.toLowerCase().equalsIgnoreCase("fast")) {
                    lamaPengerjaan = 2;
                    break;
                } else if ( paketLaundry.toLowerCase().equalsIgnoreCase("reguler")) {
                    lamaPengerjaan = 3;
                    break;
                 
                // Input "?" akan menampilkan paket yang tersedia lalu kembali meminta input
                } else if (paketLaundry.equals("?")) {
                    showPaket();                    
                    System.out.println("Masukkan paket laundry: ");
                    paketLaundry = input.nextLine();

                // Jika input jenis paket selain ketiga di atas, maka akan menampilkan pesan error dan kembali meminta input
                } else {
                    System.out.printf("Paket %s tidak diketahui\n" +
                                      "[ketik ? untuk mencari tahu jenis paket]\n", paketLaundry);
                    System.out.println("Masukkan paket laundry: ");
                    paketLaundry = input.nextLine();
                }
            }

        System.out.println("Masukan berat cucian Anda [Kg]:");  
        do {
            beratCucian = input.nextLine();                     // Meminta input berat cucian

            // Menghandle jika input berat cucian berupa string kosong
            while (!isValidInput(beratCucian)) {
                System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan positif.");
                beratCucian = input.nextLine();

                if (isValidInput(beratCucian)) break;
            }

            // Jika berat cucian < 2 kg, maka akan dianggap sebagai 2 kg
            if (Integer.parseInt(beratCucian) > 0 && Integer.parseInt(beratCucian) < 2) {
                beratCucian = "2";
                System.out.println("Cucian kurang dari " + Integer.parseInt(beratCucian) +
                                    " kg, maka cucian akan dianggap sebagai " + beratCucian + " kg");
                break;

            /* Jika input berat cucian 0 dan bilangan negatif, maka akan
            * menampilkan pesan peringatan lalu kembali meminta input
            */   
            } else if (Integer.parseInt(beratCucian) <= 0) {
                System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan positif.");
            
            // Program akan berjalan normal jika berat cucian >= 2 kg
            } else if (Integer.parseInt(beratCucian) >= 2) {
                isNumeric = true;
            }
        } while (!isNumeric);

        int berat = Integer.valueOf(beratCucian);
        
        // Membuat objek nota
        Nota nota = new Nota(memberList.get(idxMember), paketLaundry, berat, fmt.format(cal.getTime()));
        nota.setSisaHariPengerjaan(lamaPengerjaan);

        System.out.println("Berhasil menambahkan nota!");
        MainMenu.incrementNotaId();
        System.out.println(nota.generateNota(memberList.get(idxMember), paketLaundry, berat, fmt.format(cal.getTime()), bonusCounter));
        notaList.add(nota);
        //nota.getMember().getBonusCounter();
    }

    // Method untuk menghitung banyak nota dan melihat status dari cucian
    private static void handleListNota() {
        System.out.printf("Terdaftar %d nota dalam sistem.\n", notaList.size());

        for (Nota nota : notaList) {
            String getStatus = nota.getReady() ? "Sudah dapat diambil!" : "Belum bisa diambil :(";
            System.out.printf("- [%d] Status      	: %s\n", nota.getIdNota(), getStatus);
        }
    }
    
    // Method untuk menghitung banyak member di dalam sistem
    private static void handleListUser() {
        System.out.printf("Terdapat %d member dalam sistem.\n", memberList.size());

        for (int i = 0; i < memberList.size(); i++) {
            System.out.printf("- %s : %s\n", memberList.get(i).getId(), memberList.get(i).getNama());
        }
    }

    /* Method untuk mengambil cucian, dalam method ini juga dilakukan
     * pengecekan apakah cucian sudah dapat diambil atau belum
     */
    private static void handleAmbilCucian() {
        String inNota = "";
        System.out.println("Masukan ID nota yang akan diambil: ");
        inNota = input.nextLine();

        do {
            if (!isValidInput(inNota)) {
                System.out.println("ID nota berbentuk angka!");
                inNota = input.nextLine();
            } else {
                break;
            }
        } while (true);

        String status = "";

        if (findIdNota(inNota) != null) {
            if (findNotaIdx(inNota) != -1) {
                Nota nota = notaList.get(findNotaIdx(inNota));

                if (nota.getReady()) {
                    status = "berhasil diambil!";
                    notaList.remove(findIdNota(inNota));
                } else {
                    status = "gagal diambil!";
                }
            } 
            System.out.printf("Nota dengan ID %s %s\n", inNota, status);
        } else {
            System.out.printf("Nota dengan ID %s tidak ditemukan!\n", inNota);
        }
    }

    // Method untuk skip hari dan mengurangi sisa hari pencucian
    private static void handleNextDay() {
        cal.setTime(cal.getTime());
        cal.add(Calendar.DATE, 1);

        System.out.println("Dek Depe tidur hari ini... zzz...");
        for (Nota nota : notaList) {
            int sisaHari = nota.getSisaHariPengerjaan() - 1;
            nota.setSisaHariPengerjaan(sisaHari);
        
            if (nota.getSisaHariPengerjaan() == 0) {
                nota.setReady(true);
            }
        }

        for (Nota nota : notaList) {
            if (nota.getReady()) {
                System.out.printf("Laundry dengan nota ID %d sudah dapat diambil!\n", nota.getIdNota());
            }
        }
        System.out.println("Selamat pagi dunia!");
        System.out.println("Dek Depe: It's CuciCuci Time.");
    }
    
    /* Method untuk mengecek apakah ID member sudah ada di dalam list member
     * Jika ada, method ini akan mereturn true
     */
    public static boolean memberIdExists(String id) {
        for (Member idMember : memberList) {
            if (idMember.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    // Method untuk mencari index ID nota
    public static int findNotaIdx(String idNota) {
        for (int i = 0; i < notaList.size(); i++) {
            Nota nota = notaList.get(i);

            if (String.valueOf(nota.getIdNota()).equals(idNota)) {
                return i;
            }
        }
        return -1;
    }
    
    // Method untuk mencari ID nota pada list nota
    public static Nota findIdNota(String idNota) {
        for (Nota nota : notaList) {
            if (nota.getIdNota() == Integer.valueOf(idNota)) {
                return nota;
            }
        }
        return null;
    }

    // Method untuk getter dan menambah ID nota tiap generate nota
    public static void incrementNotaId() {
        counterIdNota += 1;
    }

    public static int getCounter() {
        return counterIdNota;
    }

    // Method untuk mencetak menu yang tersedia di CuciCuci
    private static void printMenu() {
        System.out.println("\nSelamat datang di CuciCuci!");
        System.out.printf("Sekarang Tanggal: %s\n", fmt.format(cal.getTime()));
        System.out.println("==============Menu==============");
        System.out.println("[1] Generate User");
        System.out.println("[2] Generate Nota");
        System.out.println("[3] List Nota");
        System.out.println("[4] List User");
        System.out.println("[5] Ambil Cucian");
        System.out.println("[6] Next Day");
        System.out.println("[0] Exit");
    }

    // Method untuk menampilkan paket yang tersedia
    private static void showPaket() {
        System.out.println("+-------------Paket-------------+");
        System.out.println("| Express | 1 Hari | 12000 / Kg |");
        System.out.println("| Fast    | 2 Hari | 10000 / Kg |");
        System.out.println("| Reguler | 3 Hari |  7000 / Kg |");
        System.out.println("+-------------------------------+");
    }
}