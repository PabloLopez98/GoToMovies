package pablo.myexample.gotomovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import pablo.myexample.gotomovies.ui.main.Movie;

public class RecyclerViewAdapterTwo extends RecyclerView.Adapter<RecyclerViewAdapterTwo.ViewHolder> {

    private ArrayList<Review> arrayList;
    private LayoutInflater mInflater;

    RecyclerViewAdapterTwo(Context context, ArrayList<Review> arrayList) {
        this.mInflater = LayoutInflater.from(context);
        this.arrayList = arrayList;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.review_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.review.setText(arrayList.get(position).getReview());
        holder.author.setText(arrayList.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView review, author;

        ViewHolder(View itemView) {
            super(itemView);
            review = itemView.findViewById(R.id.thereview);
            author = itemView.findViewById(R.id.author);
        }
    }

}