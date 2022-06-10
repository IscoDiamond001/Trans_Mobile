package com.example.trans_mobile;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Message implements Serializable {
    private int id;
    private String contenu;

    public Message() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    @NonNull
    @Override
    public String toString() {
        return "Message : "+ id ;
    }
}
