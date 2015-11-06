package twiscode.masakuuser.Model;

/**
 * Created by TwisCode-02 on 11/6/2015.
 */
public class ModelCart {
    private String nama,jumlah,harga;
    public ModelCart(){}
    public ModelCart(String nama, String jumlah, String harga){
        this.nama = nama;
        this.harga = harga;
        this.jumlah = jumlah;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }
}
