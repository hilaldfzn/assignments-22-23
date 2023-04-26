package assignments.assignment2;

import assignments.assignment1.NotaGenerator;

public class Nota {
    private Member member;
    private String paket;
    private int berat;
    private String tanggalMasuk;
    private int idNota;
    private int sisaHariPengerjaan;
    private boolean isReady;

    public Nota(Member member, String paket, int berat, String tanggalMasuk) {
        this.member = member;
        this.paket = paket;
        this.berat = berat;
        this.tanggalMasuk = tanggalMasuk;
        this.idNota = MainMenu.getCounter();
        this.sisaHariPengerjaan = 0;
        this.isReady = false;
    }
    
    public String generateNota(Member idUser, String paket, int berat, String tanggalMasuk, int bonusCounter) {
        int waktuCuci = 0, biayaPaket = 0, hargaCuci = 0;
        String formatHarga = "";
        bonusCounter = getMember().getBonusCounter();

        if (paket.toLowerCase().equals("reguler")) {
            waktuCuci = 3;                             // Paket reguler, pengerjaan 3 hari dan harganya 7000/kg
            biayaPaket = 7000;
        } else if (paket.toLowerCase().equals("fast")) {
            waktuCuci = 2;                             // Paket fast, pengerjaan 2 hari dan harganya 10000/kg
            biayaPaket = 10000;
        } else if (paket.toLowerCase().equals("express")) {
            waktuCuci = 1;                             // Paket express, pengerjaan 1 hari dan harganya 12000/kg
            biayaPaket = 12000;
        }
        
        if (bonusCounter == 3) {
            bonusCounter = 0;
            hargaCuci = (berat * biayaPaket);
            int hargaDiskon = hargaCuci / 2; 
            formatHarga = String.format(" kg x %d = %d = %d (Discount member %s)", biayaPaket, hargaCuci, hargaDiskon, "50%!!!");

        } else {
            hargaCuci = berat * biayaPaket;            // Menghitung harga laundry sesuai paket dan berat cucian
            formatHarga = String.format(" kg x %d = %d", biayaPaket, hargaCuci);
        }

        String tanggalSelesai = NotaGenerator.getDate(tanggalMasuk, waktuCuci);
        String statusCuci = getReady() ? "Sudah dapat diambil!" : "Belum bisa diambil :(";
        
        
        // Return nota dengan format id, paket, harga, tanggal terima dan tanggal selesai
        return "[ID NOTA = " + idNota + "]" + "\n" +
               "ID    : " + idUser.getId()  + "\n" +
               "Paket : " + paket + "\n" +
               "Harga :" + "\n" + berat + formatHarga + "\n" +
               "Tanggal Terima  : " + tanggalMasuk + "\n" +
               "Tanggal Selesai : " + tanggalSelesai + "\n" +
               "Status      	: " + statusCuci;
    }
    
    // Method untuk setter getter pada class Nota
    public Member getMember() {
        return member;
    }

    public String getPaket() {
        return paket;
    }

    public int getBerat() {
        return berat;
    }
    
    public String getTanggalMasuk() {
        return tanggalMasuk;
    }

    public int getIdNota() {
        return this.idNota;
    }

    public int getSisaHariPengerjaan() {
        return this.sisaHariPengerjaan;
    }

    public boolean getReady() {
        return this.isReady;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setPaket(String paket) {
        this.paket = paket;
    }

    public void setBerat(int berat) {
        this.berat = berat;
    }

    public void setTanggalMasuk(String tanggalMasuk) {
        this.tanggalMasuk = tanggalMasuk;
    }

    public void setSisaHariPengerjaan(int sisaHariPengerjaan) {
        this.sisaHariPengerjaan = sisaHariPengerjaan;
    }

    public void setReady(boolean isReady) {
        this.isReady = isReady;
    }
}