package com.elbejaj.sharetm;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
/**
 * Created by Bejaj on 21/11/2016.
 */

public class Tache {
    private int id;
    private static int counter = 0;
    private String nom;
    private String contenu;
    private int priorite;
    private Date echeance;

    public Tache(){
        super();
        counter ++;
    }

    public int getId(){
        return id;
    }

    public int getPriorite(){
        return priorite;
    }

    public String getNom(){
        return nom;
    }

    public String getContenu(){
        return contenu;
    }

    public Date getEcheance(){
        return echeance;
    }

    public void setId() { id = counter;}

    public void setNom(String tname){
        nom = tname;
    }

    public void setPriorite(int tprio){
        priorite = tprio;
    }

    public void setContenu( String tcon){
        contenu = tcon;
    }

    public void setEcheance( Date teche){
        echeance = teche;
    }
}
