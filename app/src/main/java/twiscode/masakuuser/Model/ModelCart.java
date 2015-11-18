package twiscode.masakuuser.Model;

/**
 * Created by TwisCode-02 on 11/6/2015.
 */
public class ModelCart {
    private String id,nama;
    private int jumlah, harga;
    public ModelCart(){}
    public ModelCart(String id,String nama, int jumlah, int harga){
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.jumlah = jumlah;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }
}
