package rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {

    //https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/facts.json
    public static final String BASE_URL = "https://dl.dropboxusercontent.com";
    public static final int RESP_OK       = 200;
    public static Retrofit retrofit = null;

  public static Retrofit getClient() {

        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
