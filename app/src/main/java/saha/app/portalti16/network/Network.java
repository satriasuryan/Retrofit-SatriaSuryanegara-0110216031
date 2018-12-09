package saha.app.portalti16.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {

    public static Retrofit request(){
        return new Retrofit.Builder()
                .baseUrl("https://ti16-api.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
