package com.elbejaj.sharetm;

/**
 * Created by Valentin on 01/03/2017.
 */

public class MembreGroupe {

    private String idMembreGroupe;
    private String idUtilisateur;
    private String idGroupe;
    private boolean estAdmin;

    public MembreGroupe(String idMembreGroupe, String idUtilisateur, String idGroupe, boolean estAdminGroupe) {
        this.idMembreGroupe = idMembreGroupe;
        this.idUtilisateur = idUtilisateur;
        this.idGroupe = idGroupe;
        this.estAdmin = estAdminGroupe;
    }

    public MembreGroupe(){};

    public String getIdMembreGroupe() {
        return idMembreGroupe;
    }

    public void setIdMembreGroupe(String idMembreGroupe) {
        this.idMembreGroupe = idMembreGroupe;
    }

    public String getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(String idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getIdGroupe() {
        return idGroupe;
    }

    public void setIdGroupe(String idGroupe) {
        this.idGroupe = idGroupe;
    }

    public boolean getEstAdmin() {
        return estAdmin;
    }

    public void setEstAdmin(boolean estAdminGroupe) {
        this.estAdmin = estAdminGroupe;
    }
}
