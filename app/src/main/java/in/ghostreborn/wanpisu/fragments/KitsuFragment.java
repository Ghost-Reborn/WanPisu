package in.ghostreborn.wanpisu.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.KitsuAnimeAdapter;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.model.Kitsu;
import in.ghostreborn.wanpisu.parser.KitsuAPI;
import in.ghostreborn.wanpisu.ui.KitsuLoginActivity;

public class KitsuFragment extends Fragment {

    public static boolean hasNext = false;
    public static String nextURL = "";
    RecyclerView kitsuRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kitsu, container, false);
        kitsuRecyclerView = view.findViewById(R.id.kitsu_recycler_view);

        SharedPreferences preferences = view.getContext().getSharedPreferences(WanPisuConstants.WAN_PISU_PREFERENCE, MODE_PRIVATE);
        if (!preferences.contains(WanPisuConstants.KITSU_LOGIN_FINISHED)) {
            startActivity(new Intent(view.getContext(), KitsuLoginActivity.class));
        } else {
            WanPisuConstants.kitsus = new ArrayList<>();
            KitsuAnimeSearchTask searchTask = new KitsuAnimeSearchTask(view.getContext());
            searchTask.execute();
        }

        return view;
    }

    private class KitsuAnimeSearchTask extends AsyncTask<Void, Void, ArrayList<Kitsu>> {

        Context context;

        public KitsuAnimeSearchTask(Context context) {
            this.context = context;
        }

        @Override
        protected ArrayList<Kitsu> doInBackground(Void... voids) {
            SharedPreferences preferences = context.getSharedPreferences(WanPisuConstants.WAN_PISU_PREFERENCE, MODE_PRIVATE);
            String TOKEN = preferences.getString(WanPisuConstants.KITSU_TOKEN, "");
            String USER_ID = preferences.getString(WanPisuConstants.KITSU_USER_ID, "");
            ArrayList<Kitsu> kitsus = new ArrayList<>();
            Log.e("ANIME", USER_ID);
            try {
                String URL = KitsuAPI.KITSU_API_BASE + Integer.parseInt(USER_ID) + KitsuAPI.KITSU_API_TAIL;
                if (hasNext){
                    URL = nextURL;
                }
                kitsus = KitsuAPI.getUserAnimeList(TOKEN, URL);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return kitsus;
        }

        @Override
        protected void onPostExecute(ArrayList<Kitsu> kitsus) {
            KitsuAnimeAdapter adapter = new KitsuAnimeAdapter();
            GridLayoutManager manager = new GridLayoutManager(context, 3);
            kitsuRecyclerView.setLayoutManager(manager);
            kitsuRecyclerView.setAdapter(adapter);
            if (hasNext){
                KitsuAnimeSearchTask searchTask = new KitsuAnimeSearchTask(getContext());
                searchTask.execute();
            }
        }
    }

}