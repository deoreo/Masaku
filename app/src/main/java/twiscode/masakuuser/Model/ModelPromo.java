package twiscode.masakuuser.Model;

/**
 * Created by TwisCode-02 on 11/26/2015.
 */
public class ModelPromo {
    private String id, dateStart,dateEnd, deskripsi, syarat, photo;
    private int photos;
    public ModelPromo(){}
    public ModelPromo(String id, String dateStart, String dateEnd,String deskripsi, String syarat, int photos){
        this.id = id;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.deskripsi = deskripsi;
        this.syarat = syarat;
        this.photos = photos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

   public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getSyarat() {
        return syarat;
    }

    public void setSyarat(String syarat) {
        this.syarat = syarat;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getPhotos() {
        return photos;
    }

    public void setPhotos(int photos) {
        this.photos = photos;
    }
}
