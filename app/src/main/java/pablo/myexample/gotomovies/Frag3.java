package pablo.myexample.gotomovies;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pablo.myexample.gotomovies.ui.main.Movie;

import static com.google.gson.reflect.TypeToken.get;

public class Frag3 extends Fragment implements RecyclerViewAdapter.OnMovieListener {

    private ArrayList<Movie> arrayList;
    private SavedMovieViewModel savedMovieViewModel;
    private static String API_KEY = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag3_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final int numberOfColumns = 2;
        final RecyclerView recyclerView = getView().findViewById(R.id.recycler_view_favorites);
        arrayList = new ArrayList<>();

        final RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(), arrayList, Frag3.this);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
        recyclerView.setAdapter(recyclerViewAdapter);

        savedMovieViewModel = ViewModelProviders.of(this).get(SavedMovieViewModel.class);
        savedMovieViewModel.getAllSavedMovies().observe(this, new Observer<List<SavedMovie>>() {
            @Override
            public void onChanged(@Nullable final List<SavedMovie> saved_movies) {

                arrayList.clear();

                for (int i = 0; i < saved_movies.size(); i++) {
                    int id = saved_movies.get(i).getSaved_id();
                    String path = saved_movies.get(i).getSaved_poster_path();
                    String rating = saved_movies.get(i).getSaved_rating();
                    Movie movie = new Movie(rating, path, id);
                    arrayList.add(movie);
                }

                recyclerViewAdapter.notifyDataSetChanged();

            }
        });
    }

    @Override
    public void onMovieClick(int position) {

        Movie movieObj = arrayList.get(position);
        int id = movieObj.getMovie_id();
        String theid = String.valueOf(id);
        String movie_id_url = "http://api.themoviedb.org/3/movie/" + id + "/videos?api_key=" + API_KEY;
        Intent intent = new Intent(getContext(), MovieDetails.class);
        intent.putExtra("movie_id_url", movie_id_url);
        intent.putExtra("favOrNot", "fav");
        intent.putExtra("poster_path", movieObj.getPoster_path());
        intent.putExtra("id", theid);
        startActivityForResult(intent, 1);

    }


}
