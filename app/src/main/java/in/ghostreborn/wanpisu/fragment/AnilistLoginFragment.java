package in.ghostreborn.wanpisu.fragment;

import static in.ghostreborn.wanpisu.constants.WanPisuConstants.COLUMN_ALL_ANIME_ID;
import static in.ghostreborn.wanpisu.constants.WanPisuConstants.COLUMN_ANIME_ANILIST_ID;
import static in.ghostreborn.wanpisu.constants.WanPisuConstants.COLUMN_ANIME_AVAILABLE_EPISODES;
import static in.ghostreborn.wanpisu.constants.WanPisuConstants.COLUMN_ANIME_IMAGE_URL;
import static in.ghostreborn.wanpisu.constants.WanPisuConstants.COLUMN_ANIME_MAL_ID;
import static in.ghostreborn.wanpisu.constants.WanPisuConstants.COLUMN_ANIME_NAME;
import static in.ghostreborn.wanpisu.constants.WanPisuConstants.TABLE_NAME;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.AnilistAdapter;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.database.AnilistDatabaseHelper;
import in.ghostreborn.wanpisu.model.Anilist;
import in.ghostreborn.wanpisu.parser.AnilistParser;
import in.ghostreborn.wanpisu.utils.AnilistUtils;

public class AnilistLoginFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anilist_login, container, false);
        Button anilistLoginButton = view.findViewById(R.id.anilist_login_button);
        RecyclerView animeRecyclerView = view.findViewById(R.id.anilist_recycler);
        ProgressBar anilistProgressBar = view.findViewById(R.id.anilist_progress_bar);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 3);
        animeRecyclerView.setLayoutManager(gridLayoutManager);
        if (!AnilistUtils.checkAnilist(requireContext())) {
            anilistLoginButton.setVisibility(View.VISIBLE);
            anilistLoginButton.setOnClickListener(v -> {
                AnilistUtils.checkAnilist(requireContext());
            });
        } else {
            Executor executor = Executors.newSingleThreadExecutor();
            Runnable task = () -> {
                WanPisuConstants.wanPisus = new ArrayList<>();
                String TOKEN = WanPisuConstants.preferences.getString(WanPisuConstants.WAN_PISU_ANILIST_TOKEN, "");
                String userName = WanPisuConstants.preferences.getString(WanPisuConstants.ANILIST_USER_NAME, "");
                boolean savedLocally = WanPisuConstants.preferences.getBoolean(WanPisuConstants.ANILIST_SAVED_LOCALLY, false);
                if (!savedLocally){
                    AnilistParser.getAnimeDetails(
                            userName,
                            WanPisuConstants.ANIME_CURRENT,
                            TOKEN
                    );
                    saveAnilistToDatabase();
                }else {
                    getDataFromDatabase();
                }
                requireActivity().runOnUiThread(() -> {
                    AnilistAdapter adapter = new AnilistAdapter(requireContext(), getActivity(), anilistProgressBar);
                    animeRecyclerView.setAdapter(adapter);
                    anilistLoginButton.setVisibility(View.GONE);
                });
            };
            executor.execute(task);
        }
        return view;
    }

    private void saveAnilistToDatabase(){
        AnilistDatabaseHelper helper = new AnilistDatabaseHelper(getContext());
        SQLiteDatabase db = helper.getWritableDatabase();

        for (int i=0; i<WanPisuConstants.anilists.size(); i++){

            Anilist anilist = WanPisuConstants.anilists.get(i);

            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_ANIME_ANILIST_ID, anilist.getAnilistID());
            contentValues.put(COLUMN_ALL_ANIME_ID, anilist.getAllAnimeID());
            contentValues.put(COLUMN_ANIME_MAL_ID, anilist.getMalID());
            contentValues.put(COLUMN_ANIME_NAME, anilist.getAnimeName());
            contentValues.put(COLUMN_ANIME_IMAGE_URL, anilist.getAnimeImageUrl());
            contentValues.put(COLUMN_ANIME_AVAILABLE_EPISODES, anilist.getEpisodesString());
            long test = db.insert(TABLE_NAME, null, contentValues);
            Log.e("TAG", "Anime inserted at: " + test);
        }

        WanPisuConstants.preferences.edit()
                .putBoolean(WanPisuConstants.ANILIST_SAVED_LOCALLY, true)
                .apply();

    }

    private void getDataFromDatabase(){
        AnilistDatabaseHelper helper = new AnilistDatabaseHelper(getContext());
        SQLiteDatabase db = helper.getReadableDatabase();
        WanPisuConstants.anilists = new ArrayList<>();
        Cursor cursor = db.query(WanPisuConstants.TABLE_NAME, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String anilistID = cursor.getString(0);
                String allAnimeID = cursor.getString(1);
                String malID = cursor.getString(2);
                String animeName = cursor.getString(3);
                String animeImageURL = cursor.getString(4);
                String availableEpisodes = cursor.getString(5);

                // TODO scrape available episodes from string
                WanPisuConstants.anilists.add(new Anilist(
                        animeName,
                        animeImageURL,
                        malID,
                        anilistID,
                        allAnimeID,
                        availableEpisodes
                ));
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
    }

}