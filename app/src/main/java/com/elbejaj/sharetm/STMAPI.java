package com.elbejaj.sharetm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Laurie on 01/02/2017.
 */


public class STMAPI {

    public static final String BASE_URL = "https://sharetaskmanager.gallenne.fr/v1/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        //Pour la conversion d'objets JSON
        Gson gson = new GsonBuilder()
                .setDateFormat("dd-mm-yyyy")
                .setLenient()
                .create();

        if (retrofit==null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
