package com.elbejaj.sharetm;

//@TODO : Gérer l'ajout de l'attribut de la date de création

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by Bejaj on 21/11/2016.
 */

public class Tache implements Comparable<Tache>{
    private String idTache;
    //private static final AtomicInteger count = new AtomicInteger(0);
    private String intituleT;
    private Date dateCreationT;
    private String descriptionT;
    private int prioriteT;
    private int etatT;
    private Date echeanceT;
    private String refGroupe;
    private Date dateDerniereModification;

    public Tache() {
        this.idTache = "";
        this.intituleT = "";
        this.descriptionT = "";
        this.dateCreationT = null;
        this.refGroupe = "";
        this.etatT = 0;
        this.prioriteT = 0;
        this.echeanceT = null;
        this.dateDerniereModification = null;
    }

    public Tache(String idTache, String refGroupe){
        super();
        this.refGroupe = refGroupe;
        this.idTache = idTache;
        //idTache = count.incrementAndGet();
    }

    public String getIdTache(){
        return this.idTache;
    }

    public Date getDateCreationT() {return this.dateCreationT;};

    public int getPrioriteT(){
        return this.prioriteT;
    }

    public String getRefGroupe(){
        return this.refGroupe;
    }

    public int getEtatT(){
        return etatT;
    }

    public String getIntituleT(){
        return intituleT;
    }

    public String getDescriptionT(){
        return descriptionT;
    }

    public Date getEcheanceT(){
        return echeanceT;
    }

    public Date getDateDerniereModification() {return dateDerniereModification;}

    public void setIdTache(String idTache) { this.idTache = idTache;}

    public void setDateCreationT(Date dateCreationT) {this.dateCreationT = dateCreationT;}

    public void setIntituleT(String intituleT){
        this.intituleT = intituleT;
    }

    public void setRefGroupe(String refGroupe){
        this.refGroupe = refGroupe;
    }

    public void setDateDerniereModification(Date dateDerniereModification) {this.dateDerniereModification = dateDerniereModification;}

    public void estRetard()
    {

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Log.i("test","estRetard : "+this.getDescriptionT() + this.getEcheanceT().toString());
        Date echeance = this.getEcheanceT();
        Log.i("test","echeance = "+echeance.toString());
        Date curTime= new Date();
        Boolean etat;

        if (curTime.compareTo(echeance) > 0) {
            this.setEtatT(4);
        }
    }

    public void setEtatT(int tetat){
        etatT = tetat;
    }

    public void setPrioriteT(int tprio){
        prioriteT = tprio;
    }

    public void setDescriptionT(String tcon){
        descriptionT = tcon;
    }

    public void setEcheanceT(Date teche){
        echeanceT = teche;
    }

    public int compareTo(Tache t2) {
        int i;
        if (this.getEcheanceT().compareTo(t2.getEcheanceT()) < 0){
            i = -1; // T1 est avant
        } else if (this.getEcheanceT().compareTo(t2.getEcheanceT()) > 0){
            i = 1; // T2 est avant
        } else {
            i = 0;
        }
        return i;
    }

    public static Comparator<Tache> TacheDateComparator = new Comparator<Tache>() {
        public int compare(Tache t1, Tache t2) {
            int i;
            if (t1.getEcheanceT().compareTo(t2.getEcheanceT()) < 0){
                i = -1; // T1 est avant
            } else if (t1.getEcheanceT().compareTo(t2.getEcheanceT()) > 0){
                i = 1; // T2 est avant
            } else {
                i = 0;
            }
            return i;
        }
    };

    public static Comparator<Tache> TacheEtatComparator = new Comparator<Tache>() {
        public int compare(Tache t1, Tache t2) {
            int i;
            if (t1.getEtatT() < t2.getEtatT()){
                i = -1; // T1 est avant
            } else if (t1.getEtatT() > t2.getEtatT()){
                i = 1; // T2 est avant
            } else {
                i = 0;
            }
            return i;
        }
    };

    public static Comparator<Tache> TachePrioComparator = new Comparator<Tache>() {
        public int compare(Tache t1, Tache t2) {
            int i;
            if (t1.getPrioriteT() < t2.getPrioriteT()){
                i = -1; // T1 est avant
            } else if (t1.getPrioriteT() > t2.getPrioriteT()){
                i = 1; // T2 est avant
            } else {
                i = 0;
            }
            return i;
        }
    };

    /**
     * Renvoie une chaîne de caractères représentant une tâche
     * @return
     */
    public String afficherTache() {

        String result = "";
        result = result + " Id de la tâche : " + this.getIdTache();
        result = result + " Intitulé de la tâche : " + this.getIntituleT();
        result = result + " Description de la tâche : " + this.getDescriptionT();
        result = result + " Date de création de la tâche : " + this.getDateCreationT();
        result = result + " Date d'échéance de la tâche : " + this.getEcheanceT();
        return result;


    }
}
