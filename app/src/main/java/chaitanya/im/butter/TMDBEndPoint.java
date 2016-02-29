package chaitanya.im.butter;

import retrofit2.Call;

import chaitanya.im.butter.Data.MoviePopular;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TMDBEndPoint {
    @GET("movie/popular")
    Call<MoviePopular> loadMovies(@Query("api_key") String TMDBAPI);
}
