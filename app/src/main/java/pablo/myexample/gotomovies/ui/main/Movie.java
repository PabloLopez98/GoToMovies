package pablo.myexample.gotomovies.ui.main;

public class Movie {

    private String rating;
    private String poster_path;
    private int movie_id;

    Movie() {
    }

    public Movie(String rating, String poster_path, int movie_id) {
        this.rating = rating;
        this.poster_path = poster_path;
        this.movie_id = movie_id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPosterPath() {
        this.poster_path = poster_path;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }
}
