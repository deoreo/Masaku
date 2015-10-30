package twiscode.masakuuser.Model;

/**
 * Created by User on 10/29/2015.
 */
public class ModelVendorRating {
    String id, rating, jumlahRate;
    public ModelVendorRating(String id, String rating, String jumalahRate){
        this.id = id;
        this.rating = rating;
        this.jumlahRate = jumlahRate;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getJumlahRate() {
        return jumlahRate;
    }

    public void setJumlahRate(String jumlahRate) {
        this.jumlahRate = jumlahRate;
    }
}
