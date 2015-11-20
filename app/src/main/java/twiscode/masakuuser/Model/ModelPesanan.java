package twiscode.masakuuser.Model;

/**
 * Created by User on 10/21/2015.
 */
public class ModelPesanan {
    String id, nama, status, tanggal, jam, harga,foto;

    public ModelPesanan(){

    }

    public ModelPesanan(String id, String nama, String status, String foto, String tanggal, String jam, String harga){
        this.id = id;
        this.nama = nama;
        this.status = status;
        this.tanggal = tanggal;
        this.jam = jam;
        this.harga = harga;
        this.foto = foto;
    }

    public ModelPesanan(String id, String status, String tanggal, String jam, String harga){
        this.id = id;
        this.status = status;
        this.tanggal = tanggal;
        this.jam = jam;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
