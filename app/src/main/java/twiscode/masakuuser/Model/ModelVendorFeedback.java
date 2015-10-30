package twiscode.masakuuser.Model;

/**
 * Created by User on 10/29/2015.
 */
public class ModelVendorFeedback {
    String id, nama, rate, date, feedback;

    public ModelVendorFeedback(String id, String name, String rate, String date, String feedback){
        this.id = id;
        this.nama = nama;
        this.rate = rate;
        this.date = date;
        this.feedback = feedback;

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

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
