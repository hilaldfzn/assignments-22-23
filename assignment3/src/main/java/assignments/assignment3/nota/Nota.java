package assignments.assignment3.nota;
import assignments.assignment1.NotaGenerator;
import assignments.assignment3.nota.service.LaundryService;
import assignments.assignment3.user.Member;

//import static assignments.assignment3.nota.NotaManager.*;

public class Nota {
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

    public void addService(LaundryService service) {
        LaundryService[] tempServicesList = new LaundryService[services.length + 1];

        for (int i = 0; i < services.length; i++) {
            tempServicesList[i] = services[i];
        }

        services = tempServicesList;
        tempServicesList[services.length - 1] = service;
    }

    public String kerjakan() {
        // TODO
        return "";
    }
    
    public void toNextDay() {
        // TODO
        if (isDone == false) {
            sisaHariPengerjaan--;
            if (sisaHariPengerjaan < 0) {
                this.daysLate += 1;
            }
        }
    }

    public long calculateHarga() {
        return baseHarga;
    }

    public String getNotaStatus() {
        // TODO
        if (isDone == false) {
            return "Belum selesai.";
        }
        return "Sudah selesai.";
    }

    public void setNotaStatus() {
        for(LaundryService service : services) {
            if (service.isDone() == true) this.isDone = true;
            else this.isDone = false;
        }
    }

    @Override
    public String toString() {
        return "[ID Nota = " + this.id + "]" + "\n" +
               "ID    : " + this.member.getId() + "\n" +
               "Paket : " + this.paket + "\n" +
               "Harga :\n" + this.berat + " kg x " + getHargaPaket(this.paket) + " = " + calculateHarga() + "\n" +
               "tanggal terima  : " + this.tanggalMasuk + "\n" +
               "tanggal selesai : " + NotaGenerator.getDate(this.tanggalMasuk, getHariPaket(this.paket)) + "\n" +
               "--- SERVICE LIST ---\n" + infoService();
    }

    public String infoService() {
        String outputServices = "";
        long totalHarga = calculateHarga();

        if (this.services.length != 0) {
            for (LaundryService service : services) {
                outputServices += String.format("-%s @ Rp.%d\n", service.getServiceName(), service.getHarga(berat));
                totalHarga += service.getHarga(berat);
            }

            if (this.daysLate > 0) {
                totalHarga -= Math.abs(this.sisaHariPengerjaan) * COMPENSATION_PER_DAY;
                outputServices += String.format("Harga Akhir: %d Ada kompensasi keterlambatan %d * %d hari\n", 
                                  totalHarga, this.daysLate, COMPENSATION_PER_DAY);
            } else {
                outputServices += String.format("Harga Akhir: %d\n", totalHarga);
            }
        }
        return outputServices;
    }

    private static long getHargaPaket(String paket) {
        if (paket.equalsIgnoreCase("express")) return 12000;
        if (paket.equalsIgnoreCase("fast")) return 10000;
        if (paket.equalsIgnoreCase("reguler")) return 7000;
        return -1;
    }

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

    public int getDaysLate() {
        return this.daysLate;
    }
}