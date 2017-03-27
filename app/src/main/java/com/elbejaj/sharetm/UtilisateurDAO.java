package com.elbejaj.sharetm;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Bejaj on 03/12/2016.
 */

public class UtilisateurDAO{
    private DBManager dbm;
    private SQLiteDatabase db;
    private ApiInterface apiService;     //Communication avec l'API
    private boolean isConnected;         //Indique si l'utilisateur est connecté à Internet
    private Context ctx;
    private SharedPreferences mesPreferences; //Préférences de l'application
    private String idRegisteredUser;


    public UtilisateurDAO(Context ctx, boolean isConnected)
    {
        dbm = new DBManager(ctx, "base", null, 13);
        db = dbm.getWritableDatabase();
        this.ctx = ctx;

        //Si connexion, on instancie le gestionnaire de BDD en ligne
        if(isConnected) {
            Log.i("test","Instanciation du service API");
            this.apiService = STMAPI.getClient().create(ApiInterface.class);
            this.isConnected = true;
        } else {
            this.isConnected = false;
        }

        //Récupération des données dans préférences
        this.mesPreferences = ctx.getSharedPreferences("ShareTaskManagerPreferences",0);
        this.idRegisteredUser = mesPreferences.getString("idRegisteredUser","");
        Log.i("test","CREATION UTILISATEUR DAO : ID de l'utilisateur courant : "+this.idRegisteredUser);
    }


    public void open(){
        db = dbm.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    /*
     *@brief : Fonction d'ajout d'un utilisateur
     * Retourne un long, résultat de l'insertion
     */
    public void ajouterUtilisateur(Utilisateur u){

        //@TODO : Ajouter un test pour voir si l'utilisateur n'existe pas

        long result;

        if(u!=null) {
            Log.i("test","u n'est pas null");

            //Instanciation des valeurs à donner à l'utilisateur
            ContentValues vals = new ContentValues();

            //Récupération de la date de création dans une chaîne de caractères
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String dateCreation = df.format(u.getDateCreationU());

            vals.put("idUtilisateur", u.getIdUtilisateur());
            vals.put("nomU", u.getNomU());
            vals.put("email", u.getEmail());
            vals.put("mdpHash", u.getMdpHash());
            vals.put("apiKey", u.getApiKey());
            vals.put("dateCreationU", dateCreation);
            result =  db.insert("utilisateur", null, vals);

        } else {
            Log.i("test","Utilisateur null lors de l'ajout");
        }


    }

    public int supprimerUtilisateur (int id)
    {
        return db.delete("Utilisateur", "idUtilisateur="+id, null);
    }

    public int updateUtilisateur (int id, ContentValues cv)
    {
        String nid = String.valueOf(id);
        return db.update("Utilisateur",cv,"idUtilisateur="+nid, null);
    }

    public Boolean trouverUtilisateur(String email)
    {
        Boolean trouve = false;
        Utilisateur user = null;

        //Instanciation du format de date pour la dateCreationU
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        //Requête mySql (local)
        String[] projectionIn = {"idUtilisateur","nomU","email","mdpHash","apiKey","dateCreationU"};
        String selection = "email='"+email+"'";
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String setOrder = null;
        Cursor c = db.query("utilisateur",projectionIn, selection, selectionArgs, groupBy, having, setOrder);

        if(c.getCount() > 0) {
            trouve = true;
        }

        return trouve;
    }

    /**public ArrayList <Tache> listeTache()
     {
     //Format des dates
     DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

     ArrayList<Tache> listeT = new ArrayList<Tache>();
     db = dbm.getWritableDatabase();
     Cursor c = db.query("Tache", new String[] {"idGroupe","nom","contenu","priorite","echeance","etat","groupe"},null,null,null,null,null);
     c.moveToFirst();
     while (!c.isAfterLast())
     {
     Tache t = new Tache();
     t.setIntituleT(c.getString(1));
     t.setDescriptionT(c.getString(2));
     t.setPrioriteT(c.getInt(3));
     t.setEtatT(c.getInt(5));
     t.setRefGroupe(c.getInt(6));
     String strEcheance   = c.getString(4);
     Date dateEcheance = null;
     try {
     dateEcheance = format.parse(strEcheance);
     } catch (ParseException e) {
     e.printStackTrace();
     }
     t.setEcheanceT(dateEcheance);
     t.setIdGroupe(c.getInt(0));
     listeT.add(t);
     c.moveToNext();
     }

     return listeT;
     }**/


}
