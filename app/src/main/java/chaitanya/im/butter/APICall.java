package chaitanya.im.butter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.GregorianCalendar;
import java.util.List;

import chaitanya.im.butter.Data.MovieDetail;
import chaitanya.im.butter.activity.MainActivity;
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
    AppCompatActivity _activity;
    Context _context;
    int posterW;
    boolean _refresh;
    String trailerURL;

    public APICall(String BASE_URL, String endpoint, int posterW, @Nullable Integer page, boolean refresh) {
        //http://api.themoviedb.org/3
        _BASE_URL = BASE_URL;
        this.posterW = posterW;
        _refresh = refresh;
        MainActivity.swipeRefreshLayout.setRefreshing(true);

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
                populatePosterGrid();
            }

            @Override
            public void onFailure(Call<MoviePopular> call, Throwable t) {

            }
        });
    }

    public APICall(String BASE_URL, String id, AppCompatActivity activity, Context context) {
        //http://api.themoviedb.org/3

        // Logging for retrofit
        _activity = activity;
        _context = context;

        MainActivity.swipeRefreshLayout.setRefreshing(true);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit _retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TMDBEndPoint _tmdb = _retrofit.create(TMDBEndPoint.class);

        Call<MovieDetail> _call = _tmdb.getMovieWithID(id, Keys._TMDB, "videos");
        _call.enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                updateBottomSheet(response.body());
            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {

            }
        });
    }
    public void onButtonClick() {
        _activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(trailerURL)));
    }

    public void updateBottomSheet(final MovieDetail detail) {
        final TextView title = (TextView) _activity.findViewById(R.id.bottomsheet_title);
        final ImageView backdrop_image_view = (ImageView) _activity.findViewById(R.id.backdrop);
        final TextView datetext = (TextView) _activity.findViewById(R.id.bottomsheet_date);
        final TextView runningtime = (TextView) _activity.findViewById(R.id.bottomsheet_running_time);
        final TextView description = (TextView) _activity.findViewById(R.id.bottomsheet_description);
        final NestedScrollView nestedScrollView = (NestedScrollView) _activity.findViewById(R.id.nestedScrollView);
        final NestedScrollView nestedDescription = (NestedScrollView) _activity.findViewById(R.id.nestedDescription);

        trailerURL = "http://www.youtube.com/watch?v=" +
                detail.getVideos().getResults().get(0).getKey();

        nestedScrollView.scrollTo(0, 0);
        nestedDescription.scrollTo(0,0);

        Button button = (Button) _activity.findViewById(R.id.bottomsheet_trailer_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick();
            }
        });

        title.setText(detail.getTitle());
        String[] sdate;
        sdate = detail.getReleaseDate().split("-");
        GregorianCalendar calendar = new GregorianCalendar(
                Integer.parseInt(sdate[0]),
                Integer.parseInt(sdate[1]) - 1,
                Integer.parseInt(sdate[2]));
        datetext.setText(DateFormat.getDateInstance().format(calendar.getTime()));
        runningtime.setText(detail.getRuntime().toString() + " minutes");
        description.setText(detail.getOverview());

        //Toolbar toolbar =(Toolbar) _activity.findViewById(R.id.bottomsheet_toolbar);
        //toolbar.setTitle(detail.getTitle());
        //toolbar.inflateMenu(R.menu.bottomsheet_menu);

        Log.d("APICall.java", "about to picasso");

        String backdropURL = "https://image.tmdb.org/t/p/w780" + detail.getBackdropPath();
        Picasso.with(_context)
                .load(backdropURL)
                .fit()
                .centerCrop()
                .into(backdrop_image_view, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess(){
                        Log.d("APICall.java", "Success");
                        MainActivity.bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        MainActivity.swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(){
                        Picasso.with(_context)
                                .load(R.drawable.placeholder_backdrop)
                                .fit()
                                .centerCrop()
                                .into(backdrop_image_view);

                        MainActivity.bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        MainActivity.swipeRefreshLayout.setRefreshing(false);
                    }
                });
    }


    public void populatePosterGrid() {
        String[] sdate;
        GregorianCalendar calendar;

        String basePosterURL = "https://image.tmdb.org/t/p/w" + posterW;
        String baseBackdropURL = "https://image.tmdb.org/t/p/w" + "780";
        for (int i = 0; i<_results.size(); i++) {
            _results.get(i)
                    .setFinalPosterURL(basePosterURL + _results.get(i).getPosterPath());
            _results.get(i)
                    .setFinalBackdropURL(baseBackdropURL + _results.get(i).getBackdropPath());
            //setbackdrop paths.
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
        MainActivity.updateGrid(_refresh);
    }


}
