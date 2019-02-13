package com.example.doctorhealthapp2.Model;

/**
 * Created by RONIE on 4/7/2018.
 */

public class Notification {
    public String tittle;
    public String body;

    public Notification() {
    }

    public Notification(String tittle, String body) {
        this.tittle = tittle;
        this.body = body;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
