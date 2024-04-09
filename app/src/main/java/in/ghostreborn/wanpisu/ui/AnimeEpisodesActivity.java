package in.ghostreborn.wanpisu.ui;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.AnimeEpisodesAdapter;
import in.ghostreborn.wanpisu.adapter.AnimeGroupAdapter;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.database.AnilistDatabaseHelper;
import in.ghostreborn.wanpisu.model.Anilist;
import in.ghostreborn.wanpisu.model.WanPisu;

public class AnimeEpisodesActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_episodes);

        WanPisuConstants.ALL_ANIME_EPISODE_ADD = 0;
        getDataFromDatabase();

        RecyclerView animeContainerView = findViewById(R.id.anime_episode_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        animeContainerView.setLayoutManager(manager);
        animeContainerView.setAdapter(new AnimeEpisodesAdapter(this));

        RecyclerView animeGroupContainerView = findViewById(R.id.anime_episode_group_recycler_view);
        GridLayoutManager groupManager = new GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false);
        animeGroupContainerView.setLayoutManager(groupManager);
        animeGroupContainerView.setAdapter(new AnimeGroupAdapter(getPages(), animeContainerView));

    }

    private void getDataFromDatabase() {
        AnilistDatabaseHelper helper = new AnilistDatabaseHelper(AnimeEpisodesActivity.this);
        SQLiteDatabase db = helper.getReadableDatabase();
        WanPisuConstants.anilists = new ArrayList<>();
        Cursor cursor = db.query(WanPisuConstants.TABLE_NAME, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String anilistID = cursor.getString(0);
                String allAnimeID = cursor.getString(1);
                if (!allAnimeID.equals(WanPisuConstants.ALL_ANIME_ID)) {
                    continue;
                }
                String malID = cursor.getString(2);
                String animeName = cursor.getString(3);
                String animeImageURL = cursor.getString(4);
                String availableEpisodes = cursor.getString(5);
                // TODO scrape available episodes from string
                ArrayList<String> availableEpisodesArray = new ArrayList<>();
                try {
                    JSONArray episodesArray = new JSONArray(availableEpisodes);
                    for (int i = episodesArray.length() - 1; i >=0; i--) {
                        String episode = episodesArray.getString(i);
                        availableEpisodesArray.add(episode);
                    }
                } catch (JSONException e) {
                    Log.e("TAG", e.getCause() + "");
                }
                WanPisuConstants.wanPisu = new WanPisu(
                        allAnimeID,
                        animeName,
                        animeImageURL,
                        malID,
                        availableEpisodesArray
                );
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
    }

    private int getPages() {
        int size = WanPisuConstants.wanPisu.getAvailableEpisodes().size();
        if (size % 100 == 0) {
            return size / 100;
        } else {
            return (int) (double) (size / 100) + 1;
        }
    }

}

