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

import chaitanya.im.butter.Data.DataModel;
import chaitanya.im.butter.R;

public class PosterGridAdapter extends RecyclerView.Adapter<PosterGridAdapter.ViewHolder>{
    private ArrayList<DataModel> _dataSet;
    Context _context;
    public static View.OnClickListener myOnClickListener = new MyOnClickListener();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView _ImageView;
        public TextView _TextView;

        public ViewHolder(View itemView) {
            super(itemView);
            _TextView = (TextView) itemView.findViewById(R.id.item_name);
            _ImageView = (ImageView) itemView.findViewById(R.id.item_image);
        }

    }

    public PosterGridAdapter(ArrayList<DataModel> data, Context context) {
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

        TextView textViewName = holder._TextView;
        ImageView imageView = holder._ImageView;

        textViewName.setText(_dataSet.get(listPosition).getMovieName());
        Log.d("PosterGridAdapter", "in onbindviewholder() URL = " + _dataSet.get(listPosition).getPosterURL());
        Picasso.with(_context).load(_dataSet.get(listPosition).getPosterURL()).into(imageView);
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
