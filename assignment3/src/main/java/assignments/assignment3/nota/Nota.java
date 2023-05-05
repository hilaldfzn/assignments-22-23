package assignments.assignment3.nota;

import assignments.assignment1.NotaGenerator;
import assignments.assignment3.user.Member;
import assignments.assignment3.nota.service.LaundryService;

public class Nota {
    // Properti class nota dengan visibility modifier yang sesuai
    private Member member;
    private String paket;
    private LaundryService[] services;
    private long baseHarga;
    private int sisaHariPengerjaan;
    private int daysLate;
    private int berat;
    private int id;
    private String tanggalMasuk;
    private boolean isDone;
    static public int totalNota;
    private static final long COMPENSATION_PER_DAY = 2000;

    // Constructor class nota
    public Nota(Member member, int berat, String paket, String tanggal) {
        this.member = member;
        this.berat = berat;
        this.paket = paket;
        this.tanggalMasuk = tanggal;
        baseHarga = berat * getHargaPaket(paket);
        sisaHariPengerjaan = getHariPaket(paket);
        services = new LaundryService[0];
        isDone = false;
        id = totalNota;
        totalNota++;
    }

    // Menambahkan service yang dipilih member ke array services
    public void addService(LaundryService service) {
        LaundryService[] tempServicesList = new LaundryService[services.length + 1];

        for (int i = 0; i < services.length; i++) {
            tempServicesList[i] = services[i];
        }

        services = tempServicesList;
        tempServicesList[services.length - 1] = service;
    }

    public String kerjakan() {
        String serviceStatus = "";

        for (LaundryService service : services) {   // Iterasi semua service pada array services
            if (!service.isDone()) {                // Jika masih ada service yang belum selesai, maka
                isDone = false;                     // akan tetap mereturn service yang sedang dikerjakan
                serviceStatus = service.doWork();
                break;
            }
            isDone = true;                          // Flag allServiceDone bernilai true ketika semua service selesai dikerjakan
        }

        if (isDone) {                               // Ketika semua service selesai, maka akan mengecek status nota
            serviceStatus = getNotaStatus();                 
        }
        return serviceStatus;
    }
    
    public void toNextDay() {
        if (!isDone) {
            sisaHariPengerjaan--;                   // Mengurangi 1 hari waktu pengerjaan

            if (sisaHariPengerjaan < 0) {           // Jika sisa hari pengerjaan kurang dari 0, maka hari telat akan bertambah 1 hari
                daysLate++;
            }
        }
    }

    public long calculateHarga() { 
        long calculateHarga = baseHarga;                       // Menghitung keseluruhan harga beserta servicenya

        for (LaundryService service : services) {
            calculateHarga += service.getHarga(berat); 
        }      
        return calculateHarga;                           
    }

    public String getNotaStatus() {
        return isDone ? "Sudah selesai." : "Belum selesai.";   // Status nota dicek berdasarkan flag isDone
    }

    public void setNotaStatus() {
        for (LaundryService service : services) {              // Iterasi semua service yang ada
            isDone = service.isDone() ? true : false;          // Jika semua service selesai, maka flag isDone akan bernilai true
        }
    }

    @Override
    public String toString() {                                 // Mencetak format nota beserta service listnya
        return String.format("""
                [ID Nota = %d]
                ID    : %s
                Paket : %s
                Harga :\n%d kg x %d = %d
                tanggal terima  : %s
                tanggal selesai : %s
                --- SERVICE LIST ---\n%s
                """, id, member.getId(), paket, berat, getHargaPaket(paket), baseHarga, tanggalMasuk,
                NotaGenerator.getDate(tanggalMasuk, getHariPaket(paket)), infoService());
    }

    public String infoService() {
        String outputServices = "";
        long totalHarga = calculateHarga();             

        /* Mencetak semua service yang dipilih member ke SERVICE LIST
         * Total harga akan ditambahkan harga tiap servicenya
         */
        for (LaundryService service : services) {
            outputServices += String.format("-%s @ Rp.%d%n", service.getServiceName(), service.getHarga(berat));
        }

        if (daysLate > 0) {                                        // Jika terlambat, maka member akan mendapat kompensasi
            long kompensasi = daysLate * COMPENSATION_PER_DAY;     // Kompensasi yang didapat 2000 per hari keterlambatan
            if (totalHarga <= kompensasi) {                        // Jika kompensasi melebihi total harga, maka harga bayar diset menjadi 0
                totalHarga = 0;                                    
            } else {
                totalHarga -= kompensasi;                          // Jika tidak, total harga bayar akan dikurangi kompensasi
            }
            outputServices += String.format("Harga Akhir: %d Ada kompensasi keterlambatan %d * %d hari", 
                              totalHarga, daysLate, COMPENSATION_PER_DAY);
        } else {
            outputServices += String.format("Harga Akhir: %d", totalHarga);
        }
        return outputServices;
    }

    /* Method untuk mendapatkan harga paket
     * Paket express : 12000/kg , Paket fast : 10000/kg , Paket reguler : 7000/kg
     */
    private static long getHargaPaket(String paket) {
        if (paket.equalsIgnoreCase("express")) return 12000;
        if (paket.equalsIgnoreCase("fast")) return 10000;
        if (paket.equalsIgnoreCase("reguler")) return 7000;
        return -1;
    }

    /* Method untuk mendapatkan waktu pengerjaan paket
     * Paket express : 1 hari, Paket fast : 2 hari, Paket reguler : 3 hari
     */
    private static int getHariPaket(String paket) {
        if (paket.equalsIgnoreCase("express")) return 1;
        if (paket.equalsIgnoreCase("fast")) return 2;
        if (paket.equalsIgnoreCase("reguler")) return 3;
        return -1;
    }

    // Dibawah ini adalah getter
    public String getPaket() {
        return paket;
    }

    public int getBerat() {
        return berat;
    }

    public String getTanggal() {
        return tanggalMasuk;
    }

    public int getSisaHariPengerjaan() {
        return sisaHariPengerjaan;
    }

    public boolean isDone() {
        return isDone;
    }

    public LaundryService[] getServices() {
        return services;
    }

    public int getId() {
        return id;
    }
}