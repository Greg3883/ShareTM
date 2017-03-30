package com.elbejaj.sharetm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Bejaj on 20/11/2016.
 * Activité qui démarre au lancement de l'application
 */

public class Splash extends Activity{
    MediaPlayer splashSound;
    private SharedPreferences mesPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        mesPreferences = getSharedPreferences("ShareTaskManagerPreferences",0);


        //Musique jouée au démarrage de l'application - EN PAUSE
        splashSound = MediaPlayer.create(getApplicationContext(), R.raw.splash_sound);
        //splashSound.start();

        Thread chrono = new Thread(){
            public void run(){
                try{
                    sleep(1);
                } catch (InterruptedException e){
                    e.printStackTrace();
                } finally {
                    //Si on est connecté à Internet, on démarre LoginActivity
                    //Sinon, on démarre directement le MainActivity
                    Boolean isConnected = hasActiveInternetConnection();
                    Intent i;

                    //Récupération de la valeur de userConnected dans les préférences
                    boolean userConnected = mesPreferences.getBoolean("userConnected",false);
                    String idRegisteredUser = mesPreferences.getString("idRegisteredUser","");

                    Log.i("test","SPLASH : On va voir si on est connecté");
                    if(isConnected && !userConnected) { //Connecté à Internet et pas enregistré ->Page de login
                        Log.i("test", "Connecté à Internet et pas enregistré");
                        i = new Intent(".LOGINACTIVITY");
                        i.putExtra("isConnected", isConnected);
                        //Démarrage de l'activité correcpondante
                        startActivity(i);
                    } else if (userConnected && isConnected){
                        TacheDAO td = new TacheDAO(getApplicationContext(),isConnected);
                        new SynchronisationTachesTask(getApplicationContext(),td).execute();
                        try {
                            sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        i = new Intent (".MAINACTIVITY");
                        i.putExtra("isConnected",isConnected);
                        i.putExtra("fromSplash",true);
                        //Démarrage de l'activité correcpondante
                        startActivity(i);
                    } else if (userConnected) { //User enregistré -> MainActivity contrôle la synchronisation
                        Log.i("test","Utilisateur enregistré à l'application");
                        i = new Intent (".MAINACTIVITY");
                        i.putExtra("isConnected",isConnected);
                        //Démarrage de l'activité correcpondante
                        startActivity(i);
                    } else if (!isConnected) {
                        Log.i("test","Utilisateur non connecté à Internet");
                        Toast.makeText(getApplicationContext(),"Veuillez vous connecter à Internet pour vous enregistrer",Toast.LENGTH_SHORT).show();
                    }


                }
            }
        };
        chrono.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        splashSound.release();
        finish();
    }

    /**
     * @brief Determine si le terminal est connecté à Internet
     * @return Vrai si le terminal est connecté à Internet
     */
    public boolean hasActiveInternetConnection() {
        Log.i("test","Je suis au début du hasActiveInternetConnection");
        Context context;
        Boolean isConnected = false;
        ConnectivityManager connect = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        Log.i("test","Je suis avant le if");
        if(connect!=null) {
            Log.i("test","Connect n'est pas null");

            //NetworkInfo[] information = connect.getAllNetworkInfo();
            Network[] networks = connect.getAllNetworks();
            NetworkInfo networkInfo;

            Log.i("test","J'ai récupéré les networks");


            for(Network network : networks) {
                networkInfo = connect.getNetworkInfo(network);
                if(networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    isConnected = true;
                    break;
                }
            }

        }
        Log.i("test","Je suis juste avant le retour de la fonction");
        return isConnected;
    }

}
