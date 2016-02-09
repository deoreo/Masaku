package twiscode.masakuuser.Model;

/**
 * Created by User on 10/21/2015.
 */
public class ModelUser {
    private String id, ponsel, nama, email, trusted="false";

    public ModelUser(){

    }

/*
    public ModelUser(String id, String ponsel, String nama){
        this.id = id;
        this.ponsel = ponsel;
        this.nama = nama;
        this.trusted = "false";
    }*/

    public ModelUser(String id, String ponsel, String nama, String email, String trusted){
        this.id = id;
        this.ponsel = ponsel;
        this.nama = nama;
        this.email = email;
        this.trusted = trusted;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTrusted() {
        return trusted;
    }

    public void setTrusted(String trusted) {
        this.trusted = trusted;
    }
}
