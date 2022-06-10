package com.example.trans_mobile.Compagnie;

import java.io.Serializable;
import java.util.List;

public class Compagnie implements Serializable {
    private String name;
    private String adresse;
    private int id;

    public Compagnie() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

}
