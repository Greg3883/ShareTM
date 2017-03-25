package com.elbejaj.sharetm;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

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
    @POST("register")
    Call<Utilisateur> login(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password
    );

    //Connexion d'un utilisateur
    @FormUrlEncoded
    @POST("login")
    Call<Utilisateur> login(
            @Field("email") String email,
            @Field("password") String mdpHash
    );

    //Récupération d'un utilisateur par son email
    @FormUrlEncoded
    @POST("getUserByEmail")
    Call<Utilisateur> getUserByEmail(
            @Field("email") String email
    );
    //Récupération des utilisateurs d'un groupe
    @FormUrlEncoded
    @POST("getUsersByGroupe")
    Call<List<Utilisateur>> getUsersByGroupe(
            @Field("idGroupe") String idGroupe
    );
    //*****************************************
    // GESTION TACHE
    //*****************************************

    //Récupération de toutes les tâches de la BDD
    @GET("allTasks")
    Call<List<Tache>> getAllTasks();

    //Création d'une tâche
    @FormUrlEncoded
    @POST("createTask")
    Call<Tache> createTask(
            @Field("intituleT") String intituleT,
            @Field("descriptionT") String descriptionT,
            @Field("prioriteT") int prioriteT,
            @Field("etatT") int etatT,
            @Field("echeanceT") String echeanceT,
            @Field("refGroupe") String refGroupe
    );

    //Récupération d'une tache par ID
    @FormUrlEncoded
    @POST("getTaskById")
    Call<Tache> getTaskById(
            @Field("idTache") String idTache
            );

    //Récupération des tâches par utilisateur
    @FormUrlEncoded
    @POST("getTaskByUser")
    Call<List<Tache>> getTaskByUser(
            @Field("idUtilisateur") String idUtilisateur
    );

    //Récupération des tâches par groupe
    @FormUrlEncoded
    @POST("getTaskByGroup")
    Call<List<Tache>> getTaskByGroup(
            @Field("idGroupe") String idGroupe
    );

    //*****************************************
    // GESTION AFFECTATIONTACHE
    //*****************************************

    //Création d'une affectationTâche
    @FormUrlEncoded
    @POST("createAffectationTache")
    Call<Tache> createAffectationTache(
            @Field("idUtilisateur") String idUtilisateur,
            @Field("idTache") String idTache,
            @Field("estAdmin") int estAdmin
    );

    //*****************************************
    // GESTION MEMBREGROUPE
    //*****************************************

    //Création d'une affectationTâche
    @FormUrlEncoded
    @POST("createMembreGroupe")
    Call<Tache> createMembreGroupe(
            @Field("idUtilisateur") String idUtilisateur,
            @Field("idGroupe") String idGroupe,
            @Field("estAdmin") int estAdmin
    );





}
