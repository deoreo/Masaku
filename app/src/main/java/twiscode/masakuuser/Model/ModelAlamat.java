package twiscode.masakuuser.Model;

/**
 * Created by User on 10/21/2015.
 */
public class ModelAlamat {
    private String id, nama, alamatDetail;

    public ModelAlamat(){

    }


    public ModelAlamat(String id, String nama, String alamatDetail){
        this.id = id;
        this.nama = nama;
        this.alamatDetail = alamatDetail;
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

    public String getAlamatDetail() {
        return alamatDetail;
    }

    public void setAlamatDetail(String alamatDetail) {
        this.alamatDetail = alamatDetail;
    }
}
