package assignments.assignment1;

// Import library scanner, time formatter, dan local date
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

public class NotaGenerator {
    // Membuat scanner untuk menyimpan input
    private static final Scanner sc = new Scanner(System.in);

    /* Method main
     * Program akan terus berjalan hingga user memilih menu selesai
     * Program akan menampilkan “Perintah tidak dikenali” jika pengguna memasukkan perintah selain yang tersedia */
    public static void main(String[] args) {
        boolean stopProgram = false; boolean isValidPaket = false; boolean isNumeric = false;
        int beratCucian = 0;

        while (!stopProgram) {
            // Mencetak menu yang tersedia dan meminta input menu pilihan
            printMenu();                        
            System.out.print("Pilihan : ");     
            String menuPilihan = sc.next();
            System.out.println("================================");
            
            switch (menuPilihan) {
                case "0":                // Program akan berhenti jika memilih input menu pilihan keluar dan mencetak pesan terima kasih
                    stopProgram = true;
                    System.out.println("Terima kasih telah menggunakan NotaGenerator!");
                    break;

                case "1":
                    System.out.println("Masukkan nama Anda: "); // Input nama pelanggan
                    String namaPelanggan = sc.next();           
                    sc.nextLine();

                    System.out.println("Masukkan nomor handphone Anda: "); // Input nomor handphone
                    String phoneNum = sc.nextLine();

                    // Jika input bukan digit angka, maka akan mencetak pesan dan kembali meminta input hingga sesuai
                    while (!isValidInput(phoneNum)) {
                        System.out.println("Nomor hp hanya menerima digit");
                        phoneNum = sc.nextLine();
                    }

                    // Mencetak ID pelanggan
                    System.out.println("ID Anda : " + generateId(namaPelanggan, phoneNum));
                    break;
                
                case "2":
                    System.out.println("Masukkan nama Anda: "); // Input nama pelanggan
                    namaPelanggan = sc.next();
                    sc.nextLine();

                    System.out.println("Masukkan nomor handphone Anda: "); // Input nomor handphone
                    phoneNum = sc.nextLine();
                    
                    while (!isValidInput(phoneNum)) {
                        System.out.println("Nomor hp hanya menerima digit");
                        phoneNum = sc.nextLine();
                    }
                    
                    // Input tanggal terima laundry (input dijamin valid)
                    System.out.println("Masukkan tanggal terima: ");
                    String tanggalTerima = sc.nextLine();

                    // Input jenis paket laundry yang dipilih
                    System.out.println("Masukkan paket laundry: ");
                    String paketLaundry = sc.nextLine().toLowerCase();

                    while(!isValidPaket) {
                        /* Program tidak akan mengeluarkan pesan error jika
                         * input jenis paket yang dipilih sesuai dengan
                         * express, fast, reguler */
                        if (paketLaundry.equalsIgnoreCase("Express") || 
                            paketLaundry.equalsIgnoreCase("Fast") || 
                            paketLaundry.equalsIgnoreCase("Reguler")) {
                            break;
                        } else if (paketLaundry.equals("?")) {
                            // Input "?" akan menampilkan paket yang tersedia lalu kembali meminta input
                            showPaket();                    
                            System.out.println("Masukkan paket laundry: ");
                            paketLaundry = sc.nextLine();
                        } else {
                            // Jika input jenis paket selain ketiga di atas, maka akan menampilkan pesan error dan kembali meminta input
                            System.out.printf("Paket %s tidak diketahui\n" +
                                             "[ketik ? untuk mencari tahu jenis paket]\n", paketLaundry);
                            System.out.println("Masukkan paket laundry: ");
                            paketLaundry = sc.nextLine();
                        }
                    }

                    // Input berat cucian
                    System.out.println("Masukkan berat cucian Anda [Kg]:"); 
                    do {
                        try {
                            beratCucian = sc.nextInt();     
                            sc.nextLine();

                            // Jika berat cucian < 2 kg, maka akan dianggap sebagai 2 kg
                            if (beratCucian > 0 && beratCucian < 2) {
                                beratCucian = 2;
                                System.out.println("Cucian kurang dari " + beratCucian +
                                                   " kg, maka cucian akan dianggap sebagai " +
                                                   beratCucian + " kg");
                                break;
                            /* Jika input berat cucian 0 dan bilangan negatif, maka akan
                             * menampilkan pesan peringatan lalu kembali meminta input
                             */   
                            } else if (beratCucian <= 0) {
                                System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan positif.");
                            
                            // Program akan berjalan normal jika berat cucian >= 2 kg
                            } else {
                                isNumeric = true;
                                break;
                            }
                            
                        // Exception untuk input yang bukan digit angka
                        } catch (Exception notValid) {
                            System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan positif.");
                            sc.nextLine();
                        }
                    } while (!isNumeric);
                    
                    // Mencetak nota laundry
                    System.out.println("Nota Laundry");
                    System.out.println(generateNota(generateId(namaPelanggan, phoneNum), 
                                       paketLaundry, beratCucian, tanggalTerima));
                    break;
                
                // Jika input pilihan tidak tersedia pada menu, maka akan ditampilkan pesan error
                default:
                    System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }         
        }
    }

    // Method untuk menampilkan menu di NotaGenerator.
    private static void printMenu() {
        System.out.println("Selamat datang di NotaGenerator!");
        System.out.println("==============Menu==============");
        System.out.println("[1] Generate ID");
        System.out.println("[2] Generate Nota");
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

    // Method untuk mengecek apakah input nomor handphone berupa angka atau bukan
    public static boolean isValidInput(String input) {
        if (input.equalsIgnoreCase("")) {
            return false;
        } else {
            for (int i = 0; i < input.length(); i++) {
                if(!Character.isDigit(input.charAt(i)))
                    return false;
            }
        }
        return true;
    }
    
    /* Method untuk mencari checksum yang akan ditambahkan
     * pada 2 digit terakhir pada ID pelanggan
     */
    public static int getSum(String idTemporary) {
        int sumOfChars = 0;

        for (char c : idTemporary.toCharArray()) {            // Iterasi karakter yang ada pada ID sementara
            if (Character.isDigit(c)) {                       // Karakter berupa angka akan langsung dijumlahkan
                sumOfChars += (Character.getNumericValue(c));
            } else if (Character.isLetter(c)) {               // Karakter huruf akan dikurangi 64 karena 
                sumOfChars += (int) c - 64;                   // nilai a = 1, mengikuti urutan alfabet
            } else {
                sumOfChars += 7;                              // Karakter selain huruf dan angka bernilai 7
            }
        }
        return sumOfChars;
    }
    
    // Method untuk membuat format dan tanggal selesai laundry
    public static String getDate(String tanggalAwal, int lamaCuci) {
        DateTimeFormatter formatTanggal = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate penambahanTanggal = LocalDate.parse(tanggalAwal, formatTanggal).plusDays(lamaCuci);
        String tanggalAkhir = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(penambahanTanggal);

        return tanggalAkhir;
    }
    
    /* Method untuk membuat ID dari nama dan nomor handphone.
     * return berupa String ID pelanggan dengan format:
     * [NAMADEPAN]-[nomorHP]-[2digitChecksum]
     */
    public static String generateId(String nama, String nomorHP) {
        String firstName = nama.toUpperCase().split(" ")[0];    // Mengambil nama depan pelanggan (uppercase)
        String idPelanggan = String.format("%s-%s", firstName, nomorHP);
        int checkSum = getSum(idPelanggan);

        String twoLastID = String.valueOf(checkSum);
        // Jika hasil checksum < 10, maka akan ditambah 0 pada digit awal agar menjadi 2 digit
        if (checkSum < 10) {
            idPelanggan += "-" + "0" + twoLastID;
        // Jika hasil checksum > 2 digit, maka diambil 2 digit terakhir 
        } else {
            idPelanggan += twoLastID.length() > 2 ? "-" + twoLastID.substring(twoLastID.length()-2, 
                           twoLastID.length()) :  "-" + twoLastID;
        }
        return idPelanggan;
    }

    // Method untuk membuat Nota.
    public static String generateNota(String id, String paket, int berat, String tanggalTerima) {
        int waktuCuci = 0, biayaPaket = 0;
        if (paket.equals("reguler")) {
            waktuCuci = 3;                             // Paket reguler, pengerjaan 3 hari dan harganya 7000/kg
            biayaPaket = 7000;
        } else if (paket.equals("fast")) {
            waktuCuci = 2;                             // Paket fast, pengerjaan 2 hari dan harganya 10000/kg
            biayaPaket = 10000;
        } else if (paket.equals("express")) {
            waktuCuci = 1;                             // Paket express, pengerjaan 1 hari dan harganya 12000/kg
            biayaPaket = 12000;
        }

        int hargaCuci = berat * biayaPaket;            // Menghitung harga laundry sesuai paket dan berat cucian
        String formatHarga = String.format(" kg x %d = %d", biayaPaket, hargaCuci);
        String tanggalSelesai = getDate(tanggalTerima, waktuCuci);
        
        // Return nota dengan format id, paket, harga, tanggal terima dan tanggal selesai
        return "ID    : " + id  + "\n" +
               "Paket : " + paket + "\n" +
               "Harga :" + "\n" + berat + formatHarga + "\n" +
               "Tanggal Terima  : " + tanggalTerima + "\n" +
               "Tanggal Selesai : " + tanggalSelesai;
    }
} 