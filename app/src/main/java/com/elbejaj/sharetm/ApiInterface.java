package com.elbejaj.sharetm;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
/**
 * Created by Laurie on 01/02/2017.
 */

public interface ApiInterface {

    //Récupération de toutes les tâches de la BDD
    @GET("allTasks")
    Call<List<Tache>> getAllTasks();


}
