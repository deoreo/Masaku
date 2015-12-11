package twiscode.masakuuser.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TwisCode-02 on 12/10/2015.
 */
public class ModelDetailTransaksi {
    private String type="",id="",userID="",nama="",alamat="",phone="",note="",subtotal="0",convience="0",total="0",delivery="",waktu="0",diskon="0",tip="0",status="";
    private List<ModelCart> cart = new ArrayList<>();
    public ModelDetailTransaksi(){}
    public ModelDetailTransaksi(String id, String type, String userID, String nama, String alamat, String phone, String note, String subtotal, String convience, String total, String waktu, String diskon, String tip, String delivery, String status,List<ModelCart>cart){
        this.id = id;
        this.type = type;
        this.userID = userID;
        this.nama = nama;
        this.alamat = alamat;
        this.phone = phone;
        this.note = note;
        this.subtotal = subtotal;
        this.convience = convience;
        this.total = total;
        this.diskon = diskon;
        this.delivery = delivery;
        this.waktu = waktu;
        this.tip = tip;
        this.cart = cart;
        this.status = status;
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

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getConvience() {
        return convience;
    }

    public void setConvience(String convience) {
        this.convience = convience;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getDiskon() {
        return diskon;
    }

    public void setDiskon(String diskon) {
        this.diskon = diskon;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public List<ModelCart> getCart() {
        return cart;
    }

    public void setCart(List<ModelCart> cart) {
        this.cart = cart;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
