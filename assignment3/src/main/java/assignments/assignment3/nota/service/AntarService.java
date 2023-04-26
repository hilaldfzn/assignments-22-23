package assignments.assignment3.nota.service;

//import assignments.assignment3.nota.Nota;

public class AntarService implements LaundryService {
    private boolean isDone = false;

    @Override
    public String doWork() {
        // TODO
        isDone = true;
        return "Sedang mengantar...";
    }

    @Override
    public boolean isDone() {
        // TODO
        return isDone;
    }

    @Override
    public long getHarga(int berat) {
        // TODO
        long harga = berat * 500;
        return harga < 2000 ? 2000 : harga;
    }

    @Override
    public String getServiceName() {
        return "Antar";
    }
}