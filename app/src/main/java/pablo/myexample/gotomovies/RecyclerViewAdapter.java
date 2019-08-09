package pablo.myexample.gotomovies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import pablo.myexample.gotomovies.ui.main.Movie;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<Movie> arrayList;
    private LayoutInflater mInflater;
    private OnMovieListener onMovieListener;

    RecyclerViewAdapter(Context context, ArrayList<Movie> arrayList, OnMovieListener onMovieListener) {
        this.mInflater = LayoutInflater.from(context);
        this.arrayList = arrayList;
        this.onMovieListener = onMovieListener;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.poster_card, parent, false);
        return new ViewHolder(view, onMovieListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.rating.setText(arrayList.get(position).getRating());
        String path = arrayList.get(position).getPoster_path();
        if(path.equals("no_path")){
            //DoNothing
        }else{
            Glide.with(holder.imageView.getContext()).load(path).fitCenter().into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView rating;
        ImageView imageView;
        OnMovieListener onMovieListener;

        ViewHolder(View itemView, OnMovieListener onMovieListener) {
            super(itemView);
            rating = itemView.findViewById(R.id.poster_rating);
            imageView = itemView.findViewById(R.id.poster_image);
            this.onMovieListener = onMovieListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onMovieListener.onMovieClick(getAdapterPosition());
        }
    }

    public interface OnMovieListener{
        void onMovieClick(int position);
    }

}