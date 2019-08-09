package pablo.myexample.gotomovies;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class SavedMovieViewModel extends AndroidViewModel {

    private SavedMovieRepository savedMovieRepository;

    private LiveData<List<SavedMovie>> allSavedMovies;

    private List<SavedMovie> allMovies;

    public SavedMovieViewModel(Application application) {
        super(application);
        savedMovieRepository = new SavedMovieRepository(application);
        allSavedMovies = savedMovieRepository.getAllSavedMovies();
        allMovies = savedMovieRepository.getSavedMovies();
    }

    LiveData<List<SavedMovie>> getAllSavedMovies() {
        return allSavedMovies;
    }

    List<SavedMovie> getSavedMovies() {
        return allMovies;
    }

    public void insert(SavedMovie savedMovie) {
        savedMovieRepository.insert(savedMovie);
    }

    public void delete(SavedMovie savedMovie) {
        savedMovieRepository.delete(savedMovie);
    }

    public void deleteById(Integer saved_id){
        savedMovieRepository.deleteById(saved_id);
    }

}
