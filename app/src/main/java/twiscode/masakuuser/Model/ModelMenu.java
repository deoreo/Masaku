package twiscode.masakuuser.Model;

/**
 * Created by User on 10/21/2015.
 */
public class ModelMenu {
    String id, nama, rating, foto, jumlahorder, minOrder;
    String price, time;

    public ModelMenu(){

    }

    public ModelMenu(String id, String nama, String price, String foto, String time){
        this.id = id;
        this.nama = nama;
        this.price = price;
        this.foto = foto;
        this.time = time;
    }


    public ModelMenu(String id, String nama, String rating, String foto, String jumlahorder, String minOrder){
        this.id = id;
        this.nama = nama;
        this.rating = rating;
        this.foto = foto;
        this.jumlahorder = jumlahorder;
        this.minOrder = minOrder;
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

    public String getMinOrder() {
        return minOrder;
    }

    public void setMinOrder(String minOrder) {
        this.minOrder = minOrder;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
