package in.ghostreborn.wanpisu.fragments;

import static android.content.Context.MODE_PRIVATE;
import static in.ghostreborn.wanpisu.constants.WanPisuConstants.KITSU_TOKEN;
import static in.ghostreborn.wanpisu.constants.WanPisuConstants.WAN_PISU_PREFERENCE;
import static in.ghostreborn.wanpisu.constants.WanPisuConstants.preferences;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.KitsuAnimeAdapter;
import in.ghostreborn.wanpisu.adapter.KitsuUserAnimeAdapter;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.model.Kitsu;
import in.ghostreborn.wanpisu.parser.KitsuAPI;
import in.ghostreborn.wanpisu.ui.KitsuLoginActivity;

public class KitsuUserAnimeFragment extends Fragment {

    RecyclerView kitsuUserAnimeRecycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kitsu_user_anime, container, false);
        kitsuUserAnimeRecycler = view.findViewById(R.id.kitsu_user_anime_recycler);
        TextView kitsuLoginInfoButton = view.findViewById(R.id.kitsu_login_info_button);
        kitsuLoginInfoButton.setOnClickListener(view1 -> {
            if (!preferences.contains(WanPisuConstants.KITSU_LOGIN_FINISHED)) {
                startActivity(new Intent(getContext(), KitsuLoginActivity.class));
            }
        });
        if (!preferences.contains(WanPisuConstants.KITSU_LOGIN_FINISHED)) {
            kitsuLoginInfoButton.setVisibility(View.VISIBLE);
        } else {
            String USER_ID = preferences.getString(WanPisuConstants.KITSU_USER_ID, "");
            Toast.makeText(getContext(), USER_ID, Toast.LENGTH_SHORT).show();
            WanPisuConstants.userKitsus = new ArrayList<>();
            new KitsuUserAnimeTask().execute();
        }
        return view;
    }

    private class KitsuUserAnimeTask extends AsyncTask<Void, Void, ArrayList<Kitsu>> {

        @Override
        protected ArrayList<Kitsu> doInBackground(Void... voids) {
            String TOKEN = preferences.getString(WanPisuConstants.KITSU_TOKEN, "");
            String USER_ID = preferences.getString(WanPisuConstants.KITSU_USER_ID, "");
            String URL = KitsuAPI.KITSU_API_BASE + Integer.parseInt(USER_ID) + KitsuAPI.KITSU_API_TAIL;
            if (WanPisuConstants.hasNext){
                URL = WanPisuConstants.nextURL;
            }
            KitsuAPI.getUserAnimeList(TOKEN, URL);
            return WanPisuConstants.userKitsus;
        }

        @Override
        protected void onPostExecute(ArrayList<Kitsu> kitsus) {
            KitsuUserAnimeAdapter adapter = new KitsuUserAnimeAdapter();
            GridLayoutManager manager = new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false);
            kitsuUserAnimeRecycler.setLayoutManager(manager);
            kitsuUserAnimeRecycler.setAdapter(adapter);
            if (WanPisuConstants.hasNext){
                KitsuUserAnimeTask userAnimeTask = new KitsuUserAnimeTask();
                userAnimeTask.execute();
            }
        }
    }

}