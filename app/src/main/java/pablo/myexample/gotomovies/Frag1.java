package pablo.myexample.gotomovies;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import pablo.myexample.gotomovies.ui.main.Movie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Frag1 extends Fragment implements RecyclerViewAdapter.OnMovieListener {

    private static String BASE_URL = "https://api.themoviedb.org";
    private static int PAGE = 1;
    private static String API_KEY = "9c71ca7fce2ff4e8b2f139326ba80518";
    private static String LANGUAGE = "en-US";
    private static String CATEGORY = "popular";
    private static String POSTER_PATH_BASE = "http://image.tmdb.org/t/p/w185/";
    private static ArrayList<Movie> arrayList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag1_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        callMovies();

    }

    private void callMovies() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface myInterface = retrofit.create(ApiInterface.class);
        Call<MovieResults> call = myInterface.listOfMovies(CATEGORY, API_KEY, LANGUAGE, PAGE);
        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {

                MovieResults movieResults = response.body();
                List<MovieResults.ResultsBean> listOfMovies = movieResults.getResults();

                for (int i = 0; i < listOfMovies.size(); i++) {

                    MovieResults.ResultsBean firstMovie = listOfMovies.get(i);
                    String vote_average = String.valueOf(firstMovie.getVote_average());
                    String poster_path = POSTER_PATH_BASE.concat(firstMovie.getPoster_path());
                    int movie_id = firstMovie.getId();
                    Movie movie = new Movie(vote_average, poster_path, movie_id);
                    arrayList.add(movie);

                }

                final RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(), arrayList, Frag1.this);

                RecyclerView recyclerView = getView().findViewById(R.id.recycler_view_detail);
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        if (!recyclerView.canScrollVertically(1)) {
                            callMoreMovies();
                            recyclerViewAdapter.notifyDataSetChanged();
                        }
                    }
                });
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                recyclerView.setAdapter(recyclerViewAdapter);
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private void callMoreMovies() {

        PAGE++;

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface myInterface = retrofit.create(ApiInterface.class);
        Call<MovieResults> call = myInterface.listOfMovies(CATEGORY, API_KEY, LANGUAGE, PAGE);
        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {

                MovieResults movieResults = response.body();
                List<MovieResults.ResultsBean> listOfMovies = movieResults.getResults();

                for (int i = 0; i < listOfMovies.size(); i++) {

                    MovieResults.ResultsBean firstMovie = listOfMovies.get(i);
                    String vote_average = String.valueOf(firstMovie.getVote_average());
                    String poster_path;
                    int movie_id = firstMovie.getId();

                    if (firstMovie.getPoster_path() != null) {
                        poster_path = POSTER_PATH_BASE.concat(firstMovie.getPoster_path());
                    } else {
                        poster_path = "no_path";
                    }

                    Movie movie = new Movie(vote_average, poster_path, movie_id);
                    arrayList.add(movie);

                }
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onMovieClick(final int position) {

        Movie movieObj = arrayList.get(position);
        int id = movieObj.getMovie_id();
        String theid = String.valueOf(id);
        String movie_id_url = "http://api.themoviedb.org/3/movie/" + id + "/videos?api_key=" + API_KEY;
        Intent intent = new Intent(getContext(), MovieDetails.class);
        intent.putExtra("movie_id_url", movie_id_url);
        intent.putExtra("poster_path", movieObj.getPoster_path());
        intent.putExtra("id", theid);

        startActivityForResult(intent, 1);

    }

}
