package twiscode.masakuuser.Model;

/**
 * Created by User on 10/21/2015.
 */
public class ModelUser {
    private String id, ponsel, nama;

    public ModelUser(){

    }


    public ModelUser(String id, String ponsel, String nama){
        this.id = id;
        this.ponsel = ponsel;
        this.nama = nama;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPonsel() {
        return ponsel;
    }

    public void setPonsel(String ponsel) {
        this.ponsel = ponsel;
    }


    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
