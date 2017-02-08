package com.elbejaj.sharetm;

/**
 * Created by Baalamor on 05/02/2017.
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

public class Groupe {



    //attributs
    private static final AtomicInteger count = new AtomicInteger(0);
    public int id;
    private String nomG;
    private Calendar dateC;

    //private static int id = 0;


    //construteurs
    public Groupe(String nomG, Calendar date) {
        this.id = id++;
        this.nomG = nomG;
        this.dateC = date;
    }

    public Groupe(){
        super();
        id = count.incrementAndGet();
    }

    //getters and setters
    public int getId() {
        return id;
    }

    public void setId(int i) {
         this.id = i;
    }

    public String getNom() {
        return nomG;
    }

    public void setNom(String nomG) {
        this.nomG = nomG;
    }

    public Calendar getDateC() {
        return dateC;
    }

    public void setDateC(Calendar d) {
        this.dateC = d;
    }

    public String date_toString() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(dateC.getTime());
    }
}
