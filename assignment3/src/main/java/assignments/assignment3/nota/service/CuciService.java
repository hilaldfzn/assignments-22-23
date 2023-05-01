package assignments.assignment3.nota.service;

public class CuciService implements LaundryService {
    private boolean isDone = false;

    @Override
    public String doWork() {
        isDone = true;                        // Jika dipanggil minimal sekali akan mengubah isDone() menjadi true
        return "Sedang mencuci...";
    }

    @Override
    public boolean isDone() {
        return isDone;                        // Return nilai method ini bergantung pada doWork()
    }

    @Override
    public long getHarga(int berat) {
        return 0;                             // Default harga 0 karena service ini sudah termasuk ke dalam harga paket yang dipilih
    }

    @Override
    public String getServiceName() {
        return "Cuci";                        // Nama dari service ini adalah Cuci
    }
}