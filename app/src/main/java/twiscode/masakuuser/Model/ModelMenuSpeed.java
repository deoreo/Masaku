package twiscode.masakuuser.Model;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 10/21/2015.
 */
public class ModelMenuSpeed {
    String id, nama, foto, price, time, deskripsi,hashtag,delivery,isEvent,eventName,type;
    JSONArray feedback = new JSONArray();

    public ModelMenuSpeed(){

    }

    public ModelMenuSpeed(String id, String nama, String price, String foto, String time, JSONArray feedback){
        this.id = id;
        this.nama = nama;
        this.price = price;
        this.foto = foto;
        this.time = time;
        this.feedback = feedback;
    }

    public ModelMenuSpeed(String id, String nama, String price, String foto, String time, String deskripsi, JSONArray feedback){
        this.id = id;
        this.nama = nama;
        this.price = price;
        this.foto = foto;
        this.time = time;
        this.deskripsi = deskripsi;
        this.feedback = feedback;
        this.isEvent = "false";
        this.eventName = "";
    }

    public ModelMenuSpeed(String id, String nama, String price, String foto, String time, String deskripsi, JSONArray feedback, String hashtag){
        this.id = id;
        this.nama = nama;
        this.price = price;
        this.foto = foto;
        this.time = time;
        this.deskripsi = deskripsi;
        this.feedback = feedback;
        this.hashtag = hashtag;
        this.isEvent = "false";
        this.eventName = "";
    }

    public ModelMenuSpeed(String id, String nama, String price, String foto, String time, String deskripsi, JSONArray feedback, String hashtag, String delivery){
        this.id = id;
        this.nama = nama;
        this.price = price;
        this.foto = foto;
        this.time = time;
        this.deskripsi = deskripsi;
        this.feedback = feedback;
        this.hashtag = hashtag;
        this.delivery = delivery;
        this.isEvent = "false";
        this.eventName = "";
    }

    public ModelMenuSpeed(String id, String nama, String price, String foto, String time, String deskripsi, JSONArray feedback, String hashtag, String delivery, String isEvent, String eventName){
        this.id = id;
        this.nama = nama;
        this.price = price;
        this.foto = foto;
        this.time = time;
        this.deskripsi = deskripsi;
        this.feedback = feedback;
        this.hashtag = hashtag;
        this.delivery = delivery;
        this.isEvent = isEvent;
        this.eventName = eventName;
    }

    public ModelMenuSpeed(String id, String nama, String price, String foto, String time, String deskripsi, JSONArray feedback, String hashtag, String delivery, String isEvent, String eventName, String type){
        this.id = id;
        this.nama = nama;
        this.price = price;
        this.foto = foto;
        this.time = time;
        this.deskripsi = deskripsi;
        this.feedback = feedback;
        this.hashtag = hashtag;
        this.delivery = delivery;
        this.isEvent = isEvent;
        this.eventName = eventName;
        this.type = type;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
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

    public JSONArray getFeedback() {
        return feedback;
    }

    public void setFeedback(JSONArray feedback) {
        this.feedback = feedback;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getIsEvent() {
        return isEvent;
    }

    public void setIsEvent(String isEvent) {
        this.isEvent = isEvent;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
