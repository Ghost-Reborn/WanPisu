package in.ghostreborn.wanpisu;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import in.ghostreborn.wanpisu.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Default selection is Home
        BottomNavigationView bottomNavigationView = findViewById(R.id.main_navigation_view);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.menu_home);
        menuItem.setChecked(true);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_home:
                    Fragment homeFragment = new HomeFragment();
                    transaction.replace(R.id.main_fragment_container, homeFragment);
                    transaction.commit();
                    return true;
                case R.id.menu_user:
                    return true;
                case R.id.menu_settings:
                    return true;
            }
            return false;
        });

    }

}