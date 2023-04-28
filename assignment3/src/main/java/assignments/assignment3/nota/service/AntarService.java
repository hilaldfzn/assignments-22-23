package assignments.assignment3.nota.service;

public class AntarService implements LaundryService {
    private boolean isDone = false;

    @Override
    public String doWork() {                   
        isDone = true;                         // Jika dipanggil minimal sekali akan mengubah isDone() menjadi true
        return "Sedang mengantar...";          
    }

    @Override
    public boolean isDone() {
        return isDone;                         // Return nilai method ini bergantung pada doWork()
    }

    @Override
    public long getHarga(int berat) {
        long harga = berat * 500;              // Harga antar 500/kg, dengan harga minimal 2000
        return harga < 2000 ? 2000 : harga;
    }

    @Override
    public String getServiceName() {           
        return "Antar";                        // Nama dari service ini adalah Antar
    }
}