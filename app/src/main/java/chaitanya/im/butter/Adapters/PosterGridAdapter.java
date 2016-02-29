package chaitanya.im.butter.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import chaitanya.im.butter.Data.GridDataModel;
import chaitanya.im.butter.R;

public class PosterGridAdapter extends RecyclerView.Adapter<PosterGridAdapter.ViewHolder>{
    private ArrayList<GridDataModel> _dataSet;
    Context _context;
    public static View.OnClickListener myOnClickListener = new MyOnClickListener();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView _ImageView;
        public TextView _MovieTitle;
        public TextView _ExtraInfo;

        public ViewHolder(View itemView) {
            super(itemView);
            _MovieTitle = (TextView) itemView.findViewById(R.id.movie_title);
            _ImageView = (ImageView) itemView.findViewById(R.id.item_image);
            _ExtraInfo = (TextView) itemView.findViewById(R.id.extra_info);
        }

    }

    public PosterGridAdapter(ArrayList<GridDataModel> data, Context context) {
        _dataSet = data;
        _context = context;
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

        textViewMovieTitle.setText(_dataSet.get(listPosition).getMovieName());
        textViewExtraInfo.setText(_dataSet.get(listPosition).getExtraInfo());
        Log.d("PosterGridAdapter", "in onbindviewholder() URL = " + _dataSet.get(listPosition).getPosterURL());
        Picasso.with(_context)
                .load(_dataSet.get(listPosition).getPosterURL())
                .placeholder(R.drawable.image_placeholder)
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return _dataSet.size();
    }

    private static class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Log.d("TAG", "clicked");
        }

    }
}
