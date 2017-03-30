package com.elbejaj.sharetm;

/**
 * Created by Valentin on 05/02/2017.
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Date;

public class Groupe {



    //attributs
    private static final AtomicInteger count = new AtomicInteger(0);
    public String idGroupe;
    private String nomGroupe;
    private Date dateCreationGroupe;

    //private static int idGroupe = 0;


    public Groupe() {
        this.idGroupe = "";
        this.nomGroupe = "";
        this.dateCreationGroupe = null;
        
    }
    //construteurs
    public Groupe(String idGroupe, String nomGroupe, Date date) {
        this.idGroupe = idGroupe;
        this.nomGroupe = nomGroupe;
        this.dateCreationGroupe = date;
    }

    //getters and setters
    public String getIdGroupe() {
        return idGroupe;
    }

    public void setIdGroupe(String idGroupe) {
         this.idGroupe = idGroupe;
    }

    public String getNom() {
        return nomGroupe;
    }

    public void setNom(String nomG) {
        this.nomGroupe = nomG;
    }

    public Date getDateCreationGroupe() {
        return dateCreationGroupe;
    }

    public void setDateCreationGroupe(Date d) {
        this.dateCreationGroupe = d;
    }

    public String date_toString() {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        return df.format(dateCreationGroupe);
    }

    public String toString() {
        return " Id du groupe: " + getIdGroupe()
                + " Nom du groupe: " + getNom()
                + "Date de création du groupe: " + date_toString();

    }
}
