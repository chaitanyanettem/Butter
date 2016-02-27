package chaitanya.im.butter;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APICall {
    public static String _BASE_URL;
    public static int _VERSION;
    public static Retrofit _retrofit;

    public APICall(String BASE_URL, int VERSION) {
        _BASE_URL = BASE_URL;
        _VERSION = VERSION;
        _retrofit = new Retrofit.Builder()
                .baseUrl(_BASE_URL + _VERSION)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
