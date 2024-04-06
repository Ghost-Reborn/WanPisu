package in.ghostreborn.wanpisu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.fragment.AnilistLoginFragment;
import in.ghostreborn.wanpisu.fragment.AnimeFragment;
import in.ghostreborn.wanpisu.fragment.MangaFragment;

public class MainActivity extends AppCompatActivity {

    // TODO group anime episodes by 100 and show them as pages
    // TODO Fix anilist user anime section
    // TODO Fix login button showing even though it is already logged in
    // TODO Show episode titles using jikan
    // TODO Show a way to add anime to anilist with malID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Default selection is Home
        BottomNavigationView bottomNavigationView = findViewById(R.id.main_navigation_view);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.menu_anime);
        menuItem.setChecked(true);

        getAnilistTokenFromIntentFilter();
        setData();

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_anime:
                    Fragment homeFragment = new AnimeFragment();
                    FragmentTransaction homeTransaction = getSupportFragmentManager().beginTransaction();
                    homeTransaction.replace(R.id.main_fragment_container, homeFragment);
                    homeTransaction.commit();
                    return true;
                case R.id.menu_user:
                    Fragment userFragment = new AnilistLoginFragment();
                    FragmentTransaction userTransaction = getSupportFragmentManager().beginTransaction();
                    userTransaction.replace(R.id.main_fragment_container, userFragment);
                    userTransaction.commit();
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

    private void getAnilistTokenFromIntentFilter(){
        SharedPreferences preferences = getSharedPreferences(WanPisuConstants.WAN_PISU_PREFERENCE, Context.MODE_PRIVATE);
        Intent urlIntent = getIntent();
        Uri data = urlIntent.getData();
        if (data != null){
            String url = data.toString();
            String token = url.substring(
                    url.indexOf("token=") + 6,
                    url.indexOf("&token_type")
            );
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(WanPisuConstants.WAN_PISU_ANILIST_TOKEN, token);
            editor.apply();
        }
    }

    private void setData(){
        WanPisuConstants.preferences = getSharedPreferences(WanPisuConstants.WAN_PISU_PREFERENCE, MODE_PRIVATE);
    }

}