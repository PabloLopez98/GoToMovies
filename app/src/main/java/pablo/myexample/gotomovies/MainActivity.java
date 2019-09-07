package pablo.myexample.gotomovies;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.List;

import pablo.myexample.gotomovies.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private SavedMovieViewModel savedMovieViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(this.getResources().getColor(R.color.colorAccent));
        }
        savedMovieViewModel = ViewModelProviders.of(this).get(SavedMovieViewModel.class);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        viewPager.setOffscreenPageLimit(2);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent toAttributin = new Intent(this, Attribution.class);
        startActivity(toAttributin);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        final int a = data.getIntExtra("a", 0);
        String b = data.getStringExtra("b");
        String c = data.getStringExtra("c");
        String d = data.getStringExtra("d");
        SavedMovie savedMovie = new SavedMovie(a, b, c);

        if (("yes".equalsIgnoreCase(d)) && (resultCode == RESULT_OK)) {
        } else if (!("yes".equalsIgnoreCase(d)) && (resultCode == RESULT_CANCELED)) {
        } else if (("yes".equalsIgnoreCase(d)) && (resultCode == RESULT_CANCELED)) {
            savedMovieViewModel.deleteById(a);
        } else if (!("yes".equalsIgnoreCase(d)) && (resultCode == RESULT_OK)) {
            savedMovieViewModel.insert(savedMovie);
        }
    }
}