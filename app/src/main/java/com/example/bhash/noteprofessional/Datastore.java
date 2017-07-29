package com.example.bhash.noteprofessional;

/**
 * Created by bhash on 22-07-2017.
 */

public class Datastore {
    private String head;
    private String date;
    private String data;

    public void setDate(String date) {
        this.date = date;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setHead(String head) {
        this.head = head;
    }


    public String getDate() {
        return date;
    }

    public String getHead() {
        return head;
    }

    public String getData() {
        return data;
    }
    public String toString(){
        return head+data+date;

    }
}
