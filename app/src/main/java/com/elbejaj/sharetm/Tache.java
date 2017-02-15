package com.elbejaj.sharetm;

import java.util.Comparator;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Bejaj on 21/11/2016.
 */

public class Tache implements Comparable<Tache>{
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

    public void estRetard()
    {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date echance = this.getEcheance();
        Date curTime= new Date();
        Boolean etat;

        if (curTime.compareTo(echance) > 0) {
            this.setEtat(4);
        }
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

    public int compareTo(Tache t2) {
        int i;
        if (this.getEcheance().compareTo(t2.getEcheance()) < 0){
            i = -1; // T1 est avant
        } else if (this.getEcheance().compareTo(t2.getEcheance()) > 0){
            i = 1; // T2 est avant
        } else {
            i = 0;
        }
        return i;
    }

    public static Comparator<Tache> TacheDateComparator = new Comparator<Tache>() {
        public int compare(Tache t1, Tache t2) {
            int i;
            if (t1.getEcheance().compareTo(t2.getEcheance()) < 0){
                i = -1; // T1 est avant
            } else if (t1.getEcheance().compareTo(t2.getEcheance()) > 0){
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
            if (t1.getEtat() < t2.getEtat()){
                i = -1; // T1 est avant
            } else if (t1.getEtat() > t2.getEtat()){
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
            if (t1.getPriorite() < t2.getPriorite()){
                i = -1; // T1 est avant
            } else if (t1.getPriorite() > t2.getPriorite()){
                i = 1; // T2 est avant
            } else {
                i = 0;
            }
            return i;
        }
    };
}
