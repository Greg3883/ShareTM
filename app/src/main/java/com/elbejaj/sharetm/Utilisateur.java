package com.elbejaj.sharetm;

import java.util.Date;

/**
 * Created by Boubaker-Sédike on 08/02/2017.
 */

public class Utilisateur {
    //private static final AtomicInteger count = new AtomicInteger(0);
    private String idUtilisateur;
    private String nomU;
    private String email;
    private String mdpHash;
    private String apiKey;
    private Date dateCreationU;

    public Utilisateur(){
        super();
        //idUtilisateur = count.incrementAndGet();
    }

    public String getIdUtilisateur() {
        return this.idUtilisateur;
    }

    public void setIdUtilisateur(String idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getNomU() {
        return nomU;
    }

    public void setNomU(String nomU) {
        this.nomU = nomU;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdpHash() {
        return mdpHash;
    }

    public void setMdpHash(String mdpHash) {
        this.mdpHash = mdpHash;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public Date getDateCreationU() {
        return dateCreationU;
    }

    public void setDateCreationU(Date dateCreationU) {
        this.dateCreationU = dateCreationU;
    }
}
