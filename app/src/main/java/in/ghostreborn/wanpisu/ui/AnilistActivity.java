package in.ghostreborn.wanpisu.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.AnilistFragmentStateAdapter;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;

public class AnilistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anilist);

        ViewPager2 viewPager = findViewById(R.id.anilist_view_pager);
        viewPager.setAdapter(new AnilistFragmentStateAdapter(this));
        TabLayout tabLayout = findViewById(R.id.anilist_tab_layout);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText(WanPisuConstants.ANIME_CURRENT);
                            break;
                        case 1:
                            tab.setText(WanPisuConstants.ANIME_PLANNING);
                            break;
                        case 2:
                            tab.setText(WanPisuConstants.ANIME_COMPLETED);
                            break;
                        case 3:
                            tab.setText(WanPisuConstants.ANIME_DROPPED);
                            break;
                        case 4:
                            tab.setText(WanPisuConstants.ANIME_PAUSED);
                            break;
                        case 5:
                            tab.setText(WanPisuConstants.ANIME_REPEATING);
                            break;
                    }
                }
        ).attach();

    }

}