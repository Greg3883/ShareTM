package com.elbejaj.sharetm;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Bejaj on 21/11/2016.
 */

public class Tache {
    private int id;
    private static final AtomicInteger count = new AtomicInteger(0);
    private String nom;
    private String contenu;
    private int groupe;
    private int etat;
    private int priorite;
    private Date echeance;

    public Tache(){
        super();
        groupe = 1;
        id = count.incrementAndGet();
    }

    public int getId(){
        return id;
    }

    public int getPriorite(){
        return priorite;
    }

    public int getGroupe(){
        return groupe;
    }

    public int getEtat(){
        return etat;
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

    public void setId(int tid) { id = tid;}

    public void setNom(String tname){
        nom = tname;
    }

    public void setGroupe(int tgroupe){
        groupe = tgroupe;
    }

    public void setEtat(int tetat){
        etat = tetat;
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
