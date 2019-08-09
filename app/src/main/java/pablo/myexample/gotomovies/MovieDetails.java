package pablo.myexample.gotomovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieDetails extends AppCompatActivity {

    private String API_KEY = "";
    private SavedMovieViewModel savedMovieViewModel;
    private String id, poster_path;
    private FloatingActionButton favButton;
    private boolean favOrNot;
    private String exists;
    private RecyclerView recyclerView;
    private ImageView trailer_thumbnail;
    private String pre_trailer_url;
    private Intent intent;
    private RequestQueue mQueue;
    private ImageView movie_poster;
    private TextView summary, release_date, duration, rating;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        savedMovieViewModel = ViewModelProviders.of(this).get(SavedMovieViewModel.class);
        intent = getIntent();
        id = intent.getStringExtra("id");
        int intId = Integer.parseInt(id);
        favButton = findViewById(R.id.favButton);
        for (int i = 0; i < savedMovieViewModel.getSavedMovies().size(); i++) {
            if (savedMovieViewModel.getSavedMovies().get(i).getSaved_id() == intId) {
                favButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#03DAC6")));
                favOrNot = true;
                exists = "yes";
                break;
            } else {
                favButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2196F3")));
                favOrNot = false;
                exists = "no";
            }
        }
        recyclerView = findViewById(R.id.recycler_view_detail);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//, LinearLayoutManager.HORIZONTAL, false));
        trailer_thumbnail = findViewById(R.id.trailer_view);
        movie_poster = findViewById(R.id.aa_thumbnail);
        summary = findViewById(R.id.summary_content);
        release_date = findViewById(R.id.release_date);
        duration = findViewById(R.id.runtime);
        rating = findViewById(R.id.rating);
        collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar_id);
        mQueue = Volley.newRequestQueue(this);
        pre_trailer_url = intent.getStringExtra("movie_id_url");
        poster_path = intent.getStringExtra("poster_path");
        Glide.with(this).load(poster_path).fitCenter().into(movie_poster);
        callMovie(id);
        fetchTrailer(pre_trailer_url);
        retrieveReviews(id);
    }

    private void retrieveReviews(String id) {
        final ArrayList<Review> arrayList = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/movie/" + id + "/reviews?api_key=" + API_KEY + "&language=en-US&page=1";
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject jsonObject = results.getJSONObject(i);
                        String author = jsonObject.getString("author");
                        String content = jsonObject.getString("content");
                        Review review = new Review(content, author);
                        arrayList.add(review);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                RecyclerViewAdapterTwo recyclerViewAdapterTwo = new RecyclerViewAdapterTwo(getApplicationContext(), arrayList);
                recyclerView.setAdapter(recyclerViewAdapterTwo);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }

    private void fetchTrailer(String pre_trailer_url) {
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, pre_trailer_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray results = response.getJSONArray("results");
                    JSONObject jsonObject = results.getJSONObject(0);
                    String youtube_key = jsonObject.getString("key");
                    final String toVideo = "https://www.youtube.com/watch?v=" + youtube_key;
                    String thumbnail_url = "https://img.youtube.com/vi/" + youtube_key + "/0.jpg";
                    Glide.with(getApplicationContext()).load(thumbnail_url).fitCenter().into(trailer_thumbnail);
                    trailer_thumbnail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(toVideo));
                            startActivity(i);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }

    private void callMovie(String id) {
        String url = "https://api.themoviedb.org/3/movie/" + id + "?api_key=9c71ca7fce2ff4e8b2f139326ba80518";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    String overview = response.getString("overview");
                    summary.setText(overview);
                    String release = response.getString("release_date");
                    release_date.setText(release);
                    String time = String.valueOf(response.getString("runtime")) + "m";
                    duration.setText(time);
                    String name = response.getString("original_title");
                    collapsingToolbarLayout.setTitle(name);
                    String number = String.valueOf(response.getString("vote_average"));
                    rating.setText(number);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }

    public void favOrNot(View view) {
        if (favOrNot == true) {
            favButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2196F3")));
            favOrNot = false;
            Toast.makeText(getApplicationContext(), "Not a favorite", Toast.LENGTH_SHORT).show();
        } else {
            favButton.setBackgroundTintList(ColorStateList.valueOf((Color.parseColor("#03DAC6"))));
            favOrNot = true;
            Toast.makeText(getApplicationContext(), "Favorite", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {

        if (favOrNot) {

            Intent returnIntent = new Intent();
            returnIntent.putExtra("a", Integer.parseInt(id));
            returnIntent.putExtra("b", rating.getText().toString());
            returnIntent.putExtra("c", poster_path);
            returnIntent.putExtra("d", exists);
            setResult(RESULT_OK, returnIntent);
            finish();

        } else {

            Intent returnIntent = new Intent();
            returnIntent.putExtra("a", Integer.parseInt(id));
            returnIntent.putExtra("b", rating.getText().toString());
            returnIntent.putExtra("c", poster_path);
            setResult(RESULT_CANCELED, returnIntent);
            returnIntent.putExtra("d", exists);
            finish();

        }


    }
}