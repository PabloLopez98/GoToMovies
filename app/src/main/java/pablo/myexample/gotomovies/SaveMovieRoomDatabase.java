package pablo.myexample.gotomovies;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {SavedMovie.class}, version = 1)
public abstract class SaveMovieRoomDatabase extends RoomDatabase {

    public abstract SavedMovieDao savedMovieDao();

    private static volatile SaveMovieRoomDatabase INSTANCE;

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final SavedMovieDao savedMovieDao;

        PopulateDbAsync(SaveMovieRoomDatabase db) {
            savedMovieDao = db.savedMovieDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            //savedMovieDao.deleteAll();
            //SavedMovie savedMovie = new SavedMovie(384018, "6.7", "http://image.tmdb.org/t/p/w185//keym7MPn1icW1wWfzMnW3HeuzWU.jpg");
            //savedMovieDao.insert(savedMovie);

            return null;
        }
    }

    static SaveMovieRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SaveMovieRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), SaveMovieRoomDatabase.class, "saved__movie_table").allowMainThreadQueries().build();// before allowmainthreadqueries() //.addCallback(sRoomDatabaseCallback)
                }
            }
        } return INSTANCE;
    }
}
