package chaitanya.im.butter.Adapters;

import android.content.Context;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import chaitanya.im.butter.APICall;
import chaitanya.im.butter.Data.GridDataModel;
import chaitanya.im.butter.R;

public class PosterGridAdapter extends RecyclerView.Adapter<PosterGridAdapter.ViewHolder>{
    private ArrayList<GridDataModel> _dataSet;
    Context _context;
    private AppCompatActivity _activity;
    int _posterW;
    public View.OnClickListener myOnClickListener = new MyOnClickListener();
    public final static String TAG = "PosterGridAdapter.java";

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView _ImageView;
        public TextView _MovieTitle;
        public TextView _ExtraInfo;
        public TextView _MovieID;
        public TextView _DatasetID;

        public ViewHolder(View itemView) {
            super(itemView);
            _MovieTitle = (TextView) itemView.findViewById(R.id.movie_title);
            _ImageView = (ImageView) itemView.findViewById(R.id.item_image);
            _ExtraInfo = (TextView) itemView.findViewById(R.id.extra_info);
            _MovieID = (TextView) itemView.findViewById(R.id.movie_id);
        }

    }

    public PosterGridAdapter(ArrayList<GridDataModel> data, Context context, int posterW, AppCompatActivity activity) {
        _dataSet = data;
        _context = context;
        _posterW = posterW;
        _activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_postercard, parent, false);

        view.setOnClickListener(myOnClickListener);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int listPosition) {

        TextView textViewMovieTitle = holder._MovieTitle;
        ImageView imageView = holder._ImageView;
        TextView textViewExtraInfo = holder._ExtraInfo;
        TextView textViewMovieID = holder._MovieID;
        ImageView dummyImageView = (ImageView) _activity.findViewById(R.id.dummy_image_view);

        String posterURL = _dataSet.get(listPosition).getPosterURL();
        String backdropURL = _dataSet.get(listPosition).getBackdropURL();
        textViewMovieTitle.setText(_dataSet.get(listPosition).getMovieName());
        textViewExtraInfo.setText(_dataSet.get(listPosition).getExtraInfo());
        textViewMovieID.setText(String.valueOf(_dataSet.get(listPosition).getId()));
        Log.d("PosterGridAdapter", "in onbindviewholder() URL = " + posterURL);

        int placeholder;

        if (_posterW == 185)
            placeholder = R.drawable.placeholder_small;
        else
            placeholder = R.drawable.placeholder;

        if (posterURL.endsWith("null")) {
            Picasso.with(_context)
                    .load(R.drawable.poster_unavailable)
                    .fit()
                    .centerCrop()
                    .placeholder(placeholder)
                    .into(imageView);
        }
        else {
            Picasso.with(_context)
                    .load(posterURL)
                    .fit()
                    .centerCrop()
                    .placeholder(placeholder)
                    .into(imageView);

            Picasso.with(_context)
                    .load(backdropURL)
                    .into(dummyImageView);
        }

    }

    @Override
    public int getItemCount() {
        return _dataSet.size();
    }

    private class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Log.d("TAG", "clicked");
            TextView clickedMovieID = (TextView) v.findViewById(R.id.movie_id);
            String id = clickedMovieID.getText().toString();
            new APICall("http://api.themoviedb.org/3/", id, _activity, _activity);
            Log.d(TAG, id);
            //_actionBar.hide();
        }

    }
}
