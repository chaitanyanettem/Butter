package chaitanya.im.butter;

import android.support.annotation.Nullable;

import java.text.DateFormat;
import java.util.GregorianCalendar;
import java.util.List;

import chaitanya.im.butter.Activity.MainActivity;
import chaitanya.im.butter.Data.MoviePopular;
import chaitanya.im.butter.Data.MoviePopularResults;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APICall {
    public static String _BASE_URL;
    private MoviePopular _response;
    public List<MoviePopularResults> _results;
    int posterW;


    public APICall(String BASE_URL, String endpoint, int posterW) {
        this(BASE_URL, endpoint, posterW, null);
    }

    public APICall(String BASE_URL, String endpoint, int posterW, @Nullable Integer page) {
        //http://api.themoviedb.org/3
        _BASE_URL = BASE_URL;
        this.posterW = posterW;

        // Logging for retrofit
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit _retrofit = new Retrofit.Builder()
                .baseUrl(_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TMDBEndPoint _tmdb = _retrofit.create(TMDBEndPoint.class);

        Call<MoviePopular> _call = _tmdb.loadMovies(endpoint, Keys._TMDB, page, null);
        _call.enqueue(new Callback<MoviePopular>() {
            @Override
            public void onResponse(Call<MoviePopular> call, Response<MoviePopular> response) {
                _response = response.body();
                _results = _response.getResults();
                populate_fields();
            }

            @Override
            public void onFailure(Call<MoviePopular> call, Throwable t) {

            }
        });
    }

    public void populate_fields() {
        String[] sdate;
        GregorianCalendar calendar;

        String basePosterURL = "https://image.tmdb.org/t/p/w" + posterW;
        for (int i = 0; i<_results.size(); i++) {
            _results.get(i)
                    .setFinalPosterURLs(basePosterURL + _results.get(i).getPosterPath());

            sdate = _results.get(i).getReleaseDate().split("-");

            calendar = new GregorianCalendar(
                            Integer.parseInt(sdate[0]),
                            Integer.parseInt(sdate[1]) - 1,
                            //Why on earth does month start
                            // with 0 in the GregorianCalendar?!!!
                            Integer.parseInt(sdate[2]));

            _results.get(i)
                    .setReleaseDateString(DateFormat
                            .getDateInstance()
                            .format(calendar.getTime()));
        }
        MainActivity.updateGrid(false);

    }
}
