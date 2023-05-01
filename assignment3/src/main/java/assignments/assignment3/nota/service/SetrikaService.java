package assignments.assignment3.nota.service;

public class SetrikaService implements LaundryService {
    private boolean isDone = false;

    @Override
    public String doWork() {
        isDone = true;                             // Jika dipanggil minimal sekali akan mengubah isDone() menjadi true
        return "Sedang menyetrika...";
    }

    @Override
    public boolean isDone() {
        return isDone;                             // Return nilai method ini bergantung pada doWork()
    }

    @Override
    public long getHarga(int berat) {
        return berat * 1000;                       // Harga 1000/kg, tidak ada minimal
    }

    @Override
    public String getServiceName() {
        return "Setrika";                          // Nama dari service ini adalah Setrika
    }
}