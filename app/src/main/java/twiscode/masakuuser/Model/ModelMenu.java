package twiscode.masakuuser.Model;

/**
 * Created by User on 10/21/2015.
 */
public class ModelMenu {
    String id, nama, rating, foto, jumlahorder, harga;

    public ModelMenu(){

    }

    public ModelMenu(String id, String nama, String rating, String foto, String jumlahorder, String harga){
        this.id = id;
        this.nama = nama;
        this.rating = rating;
        this.foto = foto;
        this.jumlahorder = jumlahorder;
        this.harga = harga;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getJumlahorder() {
        return jumlahorder;
    }

    public void setJumlahorder(String jumlahorder) {
        this.jumlahorder = jumlahorder;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }
}
