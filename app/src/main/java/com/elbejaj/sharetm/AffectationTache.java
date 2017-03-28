package com.elbejaj.sharetm;

/**
 * Created by Laurie on 01/03/2017.
 * Cette classe permet de stocker chaque affectation entre un utilisateur et une tâche
 * L'attribut "estAdminTache" permet de savoir si l'utilisateur affecté est administrateir de la tâche
 * (pour connaître ses droits sur la tâche en question)
 */

public class AffectationTache {

    private String idAffectationTache;
    private String idUtilisateur;
    private String idTache;
    private boolean estAdminTache; //Par défaut, on est à faux

    public String getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(String idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getIdTache() {
        return idTache;
    }

    public void setIdTache(String idTache) {
        this.idTache = idTache;
    }

    public boolean isAdmin() {
        return estAdminTache;
    }

    public void setEstAdminTache(boolean estAdminTache) {
        this.estAdminTache = estAdminTache;
    }
}
