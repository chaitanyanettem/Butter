package chaitanya.im.butter;

import android.telecom.Call;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class TMDBEndPoint {
    @GET("movie/popular?api_key=")
    Call<ResponseBody> getPopular(@Path())
}
