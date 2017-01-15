package com.elbejaj.sharetm;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Bejaj on 15/01/2017.
 */

public class Groupe {

    private int idGroupe;
    private String nomGroupe;
    private static final AtomicInteger count = new AtomicInteger(0);

    public Groupe() {
        super();

        idGroupe = count.incrementAndGet();
    }

    public int getIdGroupe() {
        return idGroupe;
    }

    public void setIdGroupe(int idGroupe) {
        this.idGroupe = idGroupe;
    }

    public String getNomGroupe() {
        return nomGroupe;
    }

    public void setNomGroupe(String nomGroupe) {
        this.nomGroupe = nomGroupe;
    }


}
