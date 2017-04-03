package com.elbejaj.sharetm;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Laurie on 01/02/2017.
 */

public interface ApiInterface {

    //*****************************************
    // GESTION UTILISATEUR
    //*****************************************

    //Inscription d'un utilisateur
    //Connexion d'un utilisateur
    @FormUrlEncoded
    @POST("inscription")
    Call<Utilisateur> register(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password
    );

    //Connexion d'un utilisateur
    @FormUrlEncoded
    @POST("connexion")
    Call<Utilisateur> login(
            @Field("email") String email,
            @Field("password") String mdpHash
    );

    //Récupération des groupes par utilisateur
    @GET("getGroupesByUtilisateur")
    Call<List<Groupe>> getGroupByUser(
            @Query("idUtilisateur") String idUtilisateur
    );

    //Récupération d'un utilisateur par son email
    @FormUrlEncoded
    @POST("getUtilisateurByEmail")
    Call<Utilisateur> getUserByEmail(
            @Field("email") String email
    );

    
    //Récupération des utilisateurs d'un groupe
    @FormUrlEncoded
    @GET("getUtilisateursByGroupe")
    Call<List<Utilisateur>> getUsersByGroupe(
            @Field("idGroupe") String idGroupe
    );
    //*****************************************
    // GESTION TACHE
    //*****************************************

    //Récupération de toutes les tâches de la BDD
    @GET("getAllTaches")
    Call<List<Tache>> getAllTasks();

    //Création d'une tâche
    @FormUrlEncoded
    @POST("creerTache")
    Call<Tache> createTask(
            @Field("intituleT") String intituleT,
            @Field("descriptionT") String descriptionT,
            @Field("prioriteT") int prioriteT,
            @Field("etatT") int etatT,
            @Field("echeanceT") String echeanceT,
            @Field("refGroupe") String refGroupe,
            @Field("idUtilisateur") String idUtilisateur
    );

    //Modification d'une tâche
    @FormUrlEncoded
    @POST("modifierTache")
    Call<Tache> updateTask(
            @Field("idTache") String idTache,
            @Field("intituleT") String intituleT,
            @Field("descriptionT") String descriptionT,
            @Field("prioriteT") int prioriteT,
            @Field("etatT") int etatT,
            @Field("echeanceT") String echeanceT
    );

    //Récupération d'une tache par ID
    @FormUrlEncoded
    @GET("getTacheById")
    Call<Tache> getTaskById(
            @Field("idTache") String idTache
    );

    //Récupération des tâches par utilisateur
    @GET("getTachesByUtilisateur")
    Call<List<Tache>> getTaskByUser(
            @Query("idUtilisateur") String idUtilisateur
    );

    //Récupération des tâches par utilisateur admin
    @GET("getTachesAdminByUtilisateur")
    Call<List<Tache>> getTaskAdminByUser(
            @Query("idUtilisateur") String idUtilisateur
    );

    //Récupération des tâches par groupe
    @FormUrlEncoded
    @GET("getTachesByGroupe")
    Call<List<Tache>> getTaskByGroup(
            @Field("idGroupe") String idGroupe
    );

    //*****************************************
    // GESTION GROUPE
    //*****************************************

    //Récupération des tâches par groupe
    @FormUrlEncoded
    @POST("creerGroupe")
    Call<Groupe> createGroupe(
            @Field("nomGroupe") String nomGroupe,
            @Field("idUtilisateur") String idUtilisateur
    );

    //uppresion tache
    @FormUrlEncoded
    @POST("supprimerTache")
    Call<Void> deleteTask(
            @Field("idTache") String idTache
    );


    @GET("getGroupeById")
    Call<List<Tache>> getGroupById(
            @Query("idGroupe") String idGroupe
    );


    //*****************************************
    // GESTION AFFECTATIONTACHE
    //*****************************************

    //Création d'une affectationTâche
    @FormUrlEncoded
    @POST("creerAffectationTache")
    Call<Tache> createAffectationTache(
            @Field("idUtilisateur") String idUtilisateur,
            @Field("idTache") String idTache,
            @Field("estAdmin") int estAdmin
    );

    //*****************************************
    // GESTION MEMBREGROUPE
    //*****************************************

    //Création d'un membre
    @FormUrlEncoded
    @POST("creerMembreGroupe")
    Call<MembreGroupe> createMembreGroupe(
            @Field("idUtilisateur") String idUtilisateur,
            @Field("idGroupe") String idGroupe,
            @Field("estAdmin") boolean estAdmin
    );

    //Liste membreGroupe par id du groupe
    @GET("getMembreByGrpId")
    Call<List<MembreGroupe>> getMembreByGrpId(
            @Query("idGroupe") String idGroupe
    );





}

