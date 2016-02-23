package chaitanya.im.butter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private ArrayList<DataModel> _dataSet;
    Context _context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView _ImageView;
        public TextView _TextView;

        public ViewHolder(View itemView) {
            super(itemView);
            _TextView = (TextView) itemView.findViewById(R.id.item_name);
            _ImageView = (ImageView) itemView.findViewById(R.id.item_image);
        }

    }

    public MyAdapter(ArrayList<DataModel> data, Context context) {
        _dataSet = data;
        _context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_postercard, parent, false);

        view.setOnClickListener(MainActivity.myOnClickListener);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int listPosition) {

        TextView textViewName = holder._TextView;
        ImageView imageView = holder._ImageView;

        textViewName.setText(_dataSet.get(listPosition).getMovieName());
        Picasso.with(_context).load("http://i.imgur.com/DvpvklR.png").into(imageView);
    }

    @Override
    public int getItemCount() {
        return _dataSet.size();
    }
}
