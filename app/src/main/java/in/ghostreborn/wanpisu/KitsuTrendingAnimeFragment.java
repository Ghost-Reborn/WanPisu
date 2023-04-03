package in.ghostreborn.wanpisu;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.adapter.KitsuAnimeAdapter;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.model.Kitsu;
import in.ghostreborn.wanpisu.parser.KitsuAPI;

public class KitsuTrendingAnimeFragment extends Fragment {

    RecyclerView kitsuTrendingRecycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kitsu_trending_anime, container, false);
        kitsuTrendingRecycler = view.findViewById(R.id.trending_anime_recycler);
        new KitsuTrendingAnimeTask().execute();
        return view;
    }

    private class KitsuTrendingAnimeTask extends AsyncTask<Void, Void, ArrayList<Kitsu>> {

        @Override
        protected ArrayList<Kitsu> doInBackground(Void... voids) {
            WanPisuConstants.kitsus = new ArrayList<>();
            KitsuAPI.getTrendingAnime();
            return WanPisuConstants.kitsus;
        }

        @Override
        protected void onPostExecute(ArrayList<Kitsu> kitsus) {
            KitsuAnimeAdapter adapter = new KitsuAnimeAdapter(WanPisuConstants.kitsus);
            GridLayoutManager manager = new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false);
            kitsuTrendingRecycler.setLayoutManager(manager);
            kitsuTrendingRecycler.setAdapter(adapter);
        }
    }

}