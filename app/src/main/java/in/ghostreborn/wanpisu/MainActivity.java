package in.ghostreborn.wanpisu;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import in.ghostreborn.wanpisu.fragment.AnimeFragment;
import in.ghostreborn.wanpisu.fragment.MangaFragment;
import in.ghostreborn.wanpisu.ui.UserActivity;

public class MainActivity extends AppCompatActivity {

    // TODO Show user anime by storing allAnime ID and parse it
    // TODO Prioritize servers and don't show it to the user
    // TODO show user's anime on clicking anime bottom navigation twice
    // TODO show user's manga on clicking manga bottom navigation twice
    // TODO by checking lastEpisodeInfo, show notification when new episode is released

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Default selection is Home
        BottomNavigationView bottomNavigationView = findViewById(R.id.main_navigation_view);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.menu_anime);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_anime:
                    Fragment homeFragment = new AnimeFragment();
                    FragmentTransaction homeTransaction = getSupportFragmentManager().beginTransaction();
                    homeTransaction.replace(R.id.main_fragment_container, homeFragment);
                    homeTransaction.commit();
                    return true;
                case R.id.menu_user:
                    startActivity(new Intent(MainActivity.this, UserActivity.class));
                    return true;
                case R.id.menu_manga:
                    Fragment mangaFragment = new MangaFragment();
                    FragmentTransaction mangaTransaction = getSupportFragmentManager().beginTransaction();
                    mangaTransaction.replace(R.id.main_fragment_container, mangaFragment);
                    mangaTransaction.commit();
                    return true;
                case R.id.menu_settings:
                    return true;
            }
            return false;
        });

    }

}