package assignments.assignment3.nota.service;

public class SetrikaService implements LaundryService {
    private boolean isDone = false;

    @Override
    public String doWork() {
        // TODO
        isDone = true;
        return "Sedang menyetrika...";
    }

    @Override
    public boolean isDone() {
        // TODO
        return isDone;
    }

    @Override
    public long getHarga(int berat) {
        // TODO
        return berat * 1000;
    }

    @Override
    public String getServiceName() {
        return "Setrika";
    }
}