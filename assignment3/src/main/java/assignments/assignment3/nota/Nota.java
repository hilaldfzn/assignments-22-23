package assignments.assignment3.nota;
import assignments.assignment1.NotaGenerator;
import assignments.assignment3.nota.service.LaundryService;
import assignments.assignment3.user.Member;

public class Nota {
    // Properti class nota dengan visibility modifier yang sesuai
    private Member member;
    private String paket;
    private LaundryService[] services;
    private long baseHarga;
    private int sisaHariPengerjaan;
    private int berat;
    private int id;
    private String tanggalMasuk;
    private boolean isDone;
    private int daysLate;
    static public int totalNota;
    private static final long COMPENSATION_PER_DAY = 2000;

    // Constructor class nota
    public Nota(Member member, int berat, String paket, String tanggal) {
        this.member = member;
        this.berat = berat;
        this.paket = paket;
        this.tanggalMasuk = tanggal;
        this.baseHarga = berat * getHargaPaket(paket);
        this.sisaHariPengerjaan = getHariPaket(paket);
        this.services = new LaundryService[0];
        this.isDone = false;
        this.id = totalNota;
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
        boolean allServiceDone = false;
        for (LaundryService service : services) {   // Iterasi semua service pada array services
            if (!service.isDone()) {                // Jika masih ada service yang belum selesai, maka
                allServiceDone = false;             // akan tetap mereturn service yang sedang dikerjakan
                return service.doWork();
            }
            allServiceDone = true;                  // Flag allServiceDone bernilai true ketika semua service selesai dikerjakan
        }
        if (allServiceDone) {                       // Ketika semua service selesai, maka akan mengecek status nota
            return getNotaStatus();                 
        }
        return "";
    }
    
    public void toNextDay() {
        if (!isDone) {
            sisaHariPengerjaan--;                    // Mengurangi 1 hari waktu pengerjaan
            if (sisaHariPengerjaan < 0) {            // Jika sisa hari pengerjaan kurang dari 0, maka hari telat akan bertambah 1 hari
                daysLate += 1;
        }
        }
    }

    public long calculateHarga() {                   // Return base harga cuci sesuai berat dan paket yang dipilih
        return baseHarga;
    }

    public String getNotaStatus() {
        return isDone ? "Sudah selesai." : "Belum selesai.";   // Status nota dicek berdasarkan flag isDone
    }

    public void setNotaStatus() {
        for(LaundryService service : services) {               // Iterasi semua service yang ada
            if (service.isDone()) isDone = true;          // Jika semua service selesai, maka flag isDone akan bernilai true
            else isDone = false;
        }
    }

    @Override
    public String toString() {
        return "[ID Nota = " + id + "]" + "\n" +
               "ID    : " + member.getId() + "\n" +
               "Paket : " + paket + "\n" +
               "Harga :\n" + berat + " kg x " + getHargaPaket(paket) + " = " + calculateHarga() + "\n" +
               "tanggal terima  : " + this.tanggalMasuk + "\n" +
               "tanggal selesai : " + NotaGenerator.getDate(tanggalMasuk, getHariPaket(paket)) + "\n" +
               "--- SERVICE LIST ---\n" + infoService();
    }

    public String infoService() {
        String outputServices = "";
        long totalHarga = calculateHarga();             

        /* Mencetak semua service yang dipilih member di SERVICE LIST
         * Total harga akan ditambahkan harga tiap servicenya
         */
        for (LaundryService service : services) {
            outputServices += String.format("-%s @ Rp.%d%n", service.getServiceName(), service.getHarga(berat));
            totalHarga += service.getHarga(berat);
        }

        if (daysLate > 0) {                                        // Jika terlambat, maka member akan mendapat kompensasi
            long kompensasi = daysLate * COMPENSATION_PER_DAY;     // Kompensasi yang didapat 2000 per hari keterlambatan
            if (totalHarga <= kompensasi) {                        // Jika kompensasi melebihi total harga, maka harga bayar diset 0
                totalHarga = 0;                                    
            } else {
                totalHarga -= kompensasi;                          // Jika tidak, total harga bayar akan dikurangi kompensasi
            }
            outputServices += String.format("Harga Akhir: %d Ada kompensasi keterlambatan %d * %d hari%n", 
                              totalHarga, daysLate, COMPENSATION_PER_DAY);
        } else {
            outputServices += String.format("Harga Akhir: %d%n", totalHarga);
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