package assignments.assignment2;

import assignments.assignment1.NotaGenerator;

public class Member {
    private String nama;
    private String noHp;
    private String id;
    private int bonusCounter;

    public Member(String nama, String noHp) {
        this.nama = nama;
        this.noHp = noHp;
        this.id = NotaGenerator.generateId(nama, noHp);
    }

    // Method untuk setter getter pada class Member
    public String getNama() {
        return nama;
    }

    public String getNoHP() {
        return noHp;
    }

    public String getId() {
        return id;
    }
    
    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setNoHP(String noHp) {
        this.noHp = noHp;
    }

    public int addBonusCounter(int bonus) {
        return bonusCounter += bonus;
    }

    public boolean isDiscount() {
        if (bonusCounter == 3) {
            bonusCounter = 0;
            return true;
        }
        return false;
    }
}