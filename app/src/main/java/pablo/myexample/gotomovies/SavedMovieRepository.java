package pablo.myexample.gotomovies;

import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

public class SavedMovieRepository {

    private SavedMovieDao savedMovieDao;
    private LiveData<List<SavedMovie>> allSavedMovies;
    private List<SavedMovie> allMovies;

    public SavedMovieRepository(Application application) {
        SaveMovieRoomDatabase db = SaveMovieRoomDatabase.getDatabase(application);
        savedMovieDao = db.savedMovieDao();
        allSavedMovies = savedMovieDao.getAllSavedMovies();
        allMovies = savedMovieDao.getSavedMovies();
    }

    LiveData<List<SavedMovie>> getAllSavedMovies() {
        return allSavedMovies;
    }

    List<SavedMovie> getSavedMovies() {
        return allMovies;
    }

    public void insert (SavedMovie savedMovie) {
        new insertAsyncTask(savedMovieDao).execute(savedMovie);
    }

    public void delete (SavedMovie savedMovie) {
        new deleteAsyncTask(savedMovieDao).execute(savedMovie);
    }

    public void deleteById(Integer saved_id){
        new deleteByIdAsyncTask(savedMovieDao).execute(saved_id);
    }

    private static class insertAsyncTask extends AsyncTask<SavedMovie, Void, Void> {

        private SavedMovieDao mAsyncTaskDao;

        insertAsyncTask(SavedMovieDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final SavedMovie... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<SavedMovie, Void, Void> {

        private SavedMovieDao mAsyncTaskDao;

        deleteAsyncTask(SavedMovieDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final SavedMovie... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    private static class deleteByIdAsyncTask extends AsyncTask<Integer, Void, Void> {

        private SavedMovieDao mAsyncTaskDao;

        deleteByIdAsyncTask(SavedMovieDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Integer... params) {
            mAsyncTaskDao.deleteById(params[0]);
            return null;
        }
    }


}
