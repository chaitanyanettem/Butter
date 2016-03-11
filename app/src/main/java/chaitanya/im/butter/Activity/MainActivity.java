package chaitanya.im.butter.Activity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import chaitanya.im.butter.APICall;
import chaitanya.im.butter.Adapters.PosterGridAdapter;
import chaitanya.im.butter.Data.GridDataModel;
import chaitanya.im.butter.EndlessScrollListener;
import chaitanya.im.butter.R;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static RecyclerView.Adapter adapter;
    private GridLayoutManager layoutManager;
    private static RecyclerView moviePosters;
    private static ArrayList<GridDataModel> data;
    private static final String BASE_URL = "http://api.themoviedb.org/3/";
    private String endpoint = "popular";
    private static APICall popularMovies;
    public final static String TAG = "MainActivity.java";
    int posterW = 0;
    int spanCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        moviePosters = (RecyclerView) findViewById(R.id.movie_posters);
        moviePosters.setHasFixedSize(true);

        getSpanAndPosterW();

        layoutManager = new GridLayoutManager(this, spanCount);
        moviePosters.setLayoutManager(layoutManager);
        moviePosters.setItemAnimator(new DefaultItemAnimator());

        popularMovies = new APICall(BASE_URL, endpoint, posterW);

        data = new ArrayList<>();
        adapter = new PosterGridAdapter(data, this, posterW);
        moviePosters.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        EndlessScrollListener mEndlessScrollListener = new EndlessScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                Log.d(TAG, "Load MOAR THINGS!!!");
                popularMovies = new APICall(BASE_URL, endpoint, posterW, page);
            }
        };

        moviePosters.addOnScrollListener(mEndlessScrollListener);
    }





    public static void updateGrid(boolean clear) {
        if(clear)
            data.clear();
        for (int i = 0; i < popularMovies._results.size(); i++) {
            data.add(new GridDataModel(
                    popularMovies._results.get(i).getTitle(),
                    popularMovies._results.get(i).getFinalPosterURLs(),
                    popularMovies._results.get(i).getReleaseDateString(),
                    popularMovies._results.get(i).getId()));
        }
        adapter.notifyDataSetChanged();
    }

    public void getSpanAndPosterW(){
        if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
                == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            spanCount = 5;
            posterW = 342;
        }
        else {
            spanCount = 3;
            posterW = 185;
        }

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int density = metrics.densityDpi;

        if (posterW == 185 && density>300) {
            Log.i(TAG, "high density screen = " +
                    density +
                    ". Setting grid poster size to w342.");
            posterW = 342;
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            if (spanCount == 5)
                spanCount = 7;

            else if (spanCount == 3 && density>=240)
                spanCount = 5;
        }
    }

    // For autocalculating number of spans.
    public class VarColumnGridLayoutManager extends GridLayoutManager {

        private int minItemWidth;

        public VarColumnGridLayoutManager(Context context, int minItemWidth) {
            super(context, 1);
            this.minItemWidth = minItemWidth;
        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler,
                                     RecyclerView.State state) {
            updateSpanCount();
            super.onLayoutChildren(recycler, state);
        }

        private void updateSpanCount() {
            Log.d("TAG", String.valueOf(getWidth()));
            int spanCount = getWidth() / minItemWidth;
            if (spanCount < 2) {
                spanCount = 2;
            }
            this.setSpanCount(spanCount);
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_discover) {
            // Handle the camera action
        } else if (id == R.id.nav_favourites) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}