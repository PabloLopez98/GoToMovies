package pablo.myexample.gotomovies;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface SavedMovieDao {

    @Insert
    void insert(SavedMovie savedMovie);

    @Delete
    void delete(SavedMovie savedMovie);

    @Query("DELETE FROM saved__movie_table WHERE saved_id = :saved_id")
    int deleteById(int saved_id);

    @Query("DELETE FROM saved__movie_table")
    void deleteAll();

    @Query("SELECT * from saved__movie_table ORDER BY id ASC")
    LiveData<List<SavedMovie>> getAllSavedMovies();

    @Query("SELECT * from saved__movie_table ORDER BY id ASC")
    List<SavedMovie> getSavedMovies();
}
