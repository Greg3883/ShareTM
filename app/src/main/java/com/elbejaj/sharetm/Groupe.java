package com.elbejaj.sharetm;

/**
 * Created by Bejaj on 04/01/2017.
 */


public class Groupe {

    //attributs
    public final int idGroupe;
    private String nomG;


    private static int id = 0;


    //construteurs
    public Groupe() {
        this.idGroupe = id++;
        this.nomG = "";
    }

    //getters and setters
    public int getId() {
        return idGroupe;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomG() {
        return nomG;
    }

    public void setNomG(String nomG) {
        this.nomG = nomG;
    }


}
