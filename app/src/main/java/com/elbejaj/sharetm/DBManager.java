package com.elbejaj.sharetm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Bejaj on 03/12/2016.
 */

public class DBManager extends SQLiteOpenHelper {

    //POUR LA TABLE TACHE
    public static final String TACHE_TABLE_NAME = "tache";

    public static final String TACHE_ID = "idTache";
    public static final String TACHE_INTITULE = "intituleT";
    public static final String TACHE_DATECREATION = "dateCreationT";
    public static final String TACHE_DESCRIPTION = "descriptionT";
    public static final String TACHE_PRIORITE = "prioriteT";
    public static final String TACHE_ETAT = "etatT";
    public static final String TACHE_ECHEANCE = "echeanceT";
    public static final String TACHE_REFGROUPE = "refGroupe";
    public static final String TACHE_DATEDERNIEREMODIFICATION = "dateDerniereModification";

    //Requête pour la création de la table Tache
    public static final String TACHE_TABLE_CREATE =
            "CREATE TABLE " + TACHE_TABLE_NAME + " (" +
                    TACHE_ID + " TEXT PRIMARY KEY, " +
                    TACHE_INTITULE + " TEXT, " +
                    TACHE_DATECREATION + " TEXT," +
                    TACHE_DESCRIPTION + " TEXT, " +
                    TACHE_PRIORITE + " INTEGER, " +
                    TACHE_ECHEANCE + " TEXT, "  +
                    TACHE_ETAT + " INTEGER, " +
                    TACHE_REFGROUPE + " TEXT, " +
                    TACHE_DATEDERNIEREMODIFICATION + " TEXT );";






    //Requête pour la suppression de la table Tache
    public static final String TACHE_TABLE_DROP = "DROP TABLE IF EXISTS " + TACHE_TABLE_NAME + ";";


    //POUR LA TABLE GROUPE
    public static final String GROUPE_TABLE_NAME = "groupe";

    public static final String GROUPE_ID = "idGroupe";
    public static final String GROUPE_NOM = "nomGroupe";
    public static final String GROUPE_DATECREATION = "dateCreationGroupe";

    //Requête pour la création de la table groupe
    public static final String GROUPE_TABLE_CREATE =
            "CREATE TABLE " + GROUPE_TABLE_NAME + " (" +
                    GROUPE_ID + " TEXT PRIMARY KEY, " +
                    GROUPE_NOM + " TEXT, " +
                    GROUPE_DATECREATION + " TEXT "+ ");";

    //Requête pour la suppression de la table groupe
    public static final String GROUPE_TABLE_DROP = "DROP TABLE IF EXISTS " + GROUPE_TABLE_NAME + ";";

    //POUR LA TABLE AFFTACHE
    public static final String AFFTACHE_TABLE_NAME = "affectationTache";

    public static final String AFFTACHE_ID = "idAffectTache";
    public static final String AFFTACHE_ESTPROP = "estAdminTache";
    public static final String AFFTACHE_IDU = "idUtilisateur";
    public static final String AFFTACHE_IDT = "idTache";

    //Requête pour la création de la table groupe
    public static final String AFFTACHE_TABLE_CREATE =
            "CREATE TABLE " + AFFTACHE_TABLE_NAME + " (" +
                    AFFTACHE_ID + " TEXT PRIMARY KEY, " +
                    AFFTACHE_ESTPROP + " INTEGER, " +
                    AFFTACHE_IDU + " TEXT, " +
                    AFFTACHE_IDT + "TEXT "+ ");";

    //Requête pour la suppression de la table groupe
    public static final String AFFTACHE_TABLE_DROP = "DROP TABLE IF EXISTS " + AFFTACHE_TABLE_NAME + ";";

    //POUR LA TABLE UTILISATEUR
    public static final String UTILISATEUR_ID = "idUtilisateur";
    public static final String UTILISATEUR_NOMU = "nomU";
    public static final String UTILISATEUR_EMAIL = "email";
    public static final String UTILISATEUR_MDPHASH = "mdpHash";
    public static final String UTILISATEUR_APIKEY ="apiKey";
    public static final String UTILISATEUR_DATECREATION = "dateCreationU";

    //Requête pour la création de la table Utilisateur
    public static final String UTILISATEUR_TABLE_CREATE =
            "CREATE TABLE " + "utilisateur" + " (" +
                    UTILISATEUR_ID + " TEXT PRIMARY KEY, " +
                    UTILISATEUR_NOMU + " TEXT, " +
                    UTILISATEUR_EMAIL + " TEXT, " +
                    UTILISATEUR_MDPHASH + " TEXT, "+
                    UTILISATEUR_APIKEY + " TEXT, "+
                    UTILISATEUR_DATECREATION + " TEXT "+");";

    //Requête pour la suppression de la table
    public static final String UTILISATEUR_TABLE_DROP = "DROP TABLE IF EXISTS " + "utilisateur" + ";";



    //Gestion de la table membreGroupe

    public static final String MEMBGRP_TABLE_NAME = "membreGroupe";

    public static final String MEMBGRP_ID = "idMembreGroupe";
    public static final String MEMBGRP_IDU = "idUtilisateur";
    public static final String MEMBGRP_IDG = "idGroupe";
    public static final String MEMBGRP_ISADMIN = "estAdmin";

    public static final String MEMBGRP_TABLE_CREATE =
            "CREATE TABLE " + MEMBGRP_TABLE_NAME + " (" +
                    MEMBGRP_ID + " TEXT PRIMARY KEY, " +
                    MEMBGRP_IDU + " TEXT, " +
                    MEMBGRP_IDG + " TEXT, " +
                    MEMBGRP_ISADMIN + " INTEGER "+ ");";

    //Requête pour la suppression de la table
    public static final String MEMBGRP_TABLE_DROP = "DROP TABLE IF EXISTS " + MEMBGRP_TABLE_NAME + ";";


    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(TACHE_TABLE_CREATE);
            db.execSQL(AFFTACHE_TABLE_CREATE);
            db.execSQL(GROUPE_TABLE_CREATE);
            db.execSQL(UTILISATEUR_TABLE_CREATE);
            db.execSQL(MEMBGRP_TABLE_CREATE);
        } catch (Exception e) {
            Log.i("test","Problème création DB, " + e.getMessage());
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TACHE_TABLE_DROP);
        db.execSQL(GROUPE_TABLE_DROP);
        db.execSQL(AFFTACHE_TABLE_DROP);
        db.execSQL(UTILISATEUR_TABLE_DROP);
        db.execSQL(MEMBGRP_TABLE_DROP);
        onCreate(db);
    }


}
