package pablo.myexample.gotomovies;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "saved__movie_table")
public class SavedMovie {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private int saved_id;

    @NonNull
    private String saved_poster_path;

    @NonNull
    private String saved_rating;

    public SavedMovie(int saved_id, String saved_rating, String saved_poster_path){
        this.saved_id = saved_id;
        this.saved_rating = saved_rating;
        this.saved_poster_path = saved_poster_path;
    }

    @NonNull
    public int getSaved_id() {
        return saved_id;
    }

    public void setSaved_id(@NonNull int saved_id) {
        this.saved_id = saved_id;
    }

    @NonNull
    public String getSaved_poster_path() {
        return saved_poster_path;
    }

    public void setSaved_poster_path(@NonNull String saved_poster_path) {
        this.saved_poster_path = saved_poster_path;
    }

    @NonNull
    public String getSaved_rating() {
        return saved_rating;
    }

    public void setSaved_rating(@NonNull String saved_rating) {
        this.saved_rating = saved_rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
