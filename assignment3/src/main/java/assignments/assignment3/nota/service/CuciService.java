package assignments.assignment3.nota.service;

public class CuciService implements LaundryService {
    private boolean isDone = false;

    @Override
    public String doWork() {
        // TODO
        isDone = true;
        return "Sedang mencuci...";
    }

    @Override
    public boolean isDone() {
        // TODO
        return isDone;
    }

    @Override
    public long getHarga(int berat) {
        // TODO
        return 0;
    }

    @Override
    public String getServiceName() {
        return "Cuci";
    }
}