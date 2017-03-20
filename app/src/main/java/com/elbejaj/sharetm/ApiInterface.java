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

    //Connexion d'un utilisateur
    @FormUrlEncoded
    @POST("login")
    Call<Utilisateur> login(
            @Field("email") String email,
            @Field("password") String mdpHash
    );

    //Récupération de toutes les tâches de la BDD
    @GET("allTasks")
    Call<List<Tache>> getAllTasks();




}
