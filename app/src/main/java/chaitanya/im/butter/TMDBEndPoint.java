package chaitanya.im.butter;

import retrofit2.Call;

import chaitanya.im.butter.Data.MoviePopular;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDBEndPoint {
    @GET("movie/{endpoint}")
    Call<MoviePopular> loadMovies(@Path("endpoint") String endpoint,
                                  @Query("api_key") String TMDBKey,
                                  @Query("page") Integer i,
                                  @Query("language") String language);

    @GET("movie/latest")
    Call<MoviePopular> latestMovies(@Query("api_key") String TMDBAPI);
}
