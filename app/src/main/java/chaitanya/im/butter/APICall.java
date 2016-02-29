package chaitanya.im.butter;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

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
    private List<MoviePopularResults> _results;
    public List<Integer> _id;
    public List<String> _titles;
    public List<String> _posterURLs;
    public int _size;
    public List<String> _releaseDate;


    public APICall(String BASE_URL) {
        //http://api.themoviedb.org/3
        _BASE_URL = BASE_URL;

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
        Call<MoviePopular> _call = _tmdb.loadMovies("popular", Keys._TMDB, null, "hi");

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
        _posterURLs = new ArrayList<>();
        _titles = new ArrayList<>();
        _id = new ArrayList<>();
        _releaseDate = new ArrayList<>();
        _size = _results.size();
        String[] sdate;
        GregorianCalendar calendar;

        String basePosterURL = "https://image.tmdb.org/t/p/w342";
        for (int i = 0; i<_results.size(); i++) {
            _titles.add(_results.get(i).getTitle());
            _posterURLs.add(basePosterURL + _results.get(i).getPosterPath());
            _id.add(_results.get(i).getId());

            sdate = _results.get(i).getReleaseDate().split("-");
            calendar = new GregorianCalendar(
                            Integer.parseInt(sdate[0]),
                            Integer.parseInt(sdate[1]),
                            Integer.parseInt(sdate[2]));

            _releaseDate.add(DateFormat
                    .getDateInstance()
                    .format(calendar.getTime()));
        }
        MainActivity.updateGrid();

    }
}
