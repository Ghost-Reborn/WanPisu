package in.ghostreborn.wanpisu.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.KitsuAnimeAdapter;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.model.Kitsu;
import in.ghostreborn.wanpisu.parser.KitsuAPI;

public class KitsuFragment extends Fragment {

    RecyclerView kitsuRecyclerView;
    ProgressBar kitsuProgressBar;
    boolean isSearching = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kitsu, container, false);
        kitsuRecyclerView = view.findViewById(R.id.kitsu_recycler_view);
        kitsuProgressBar = view.findViewById(R.id.kitsu_recycler_progress);

        KitsuAnimeSearchTask searchTask = new KitsuAnimeSearchTask(view.getContext(), "");
        searchTask.execute();

        SearchView kitsuSearchView = view.findViewById(R.id.kitsu_search_view);
        kitsuSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                isSearching = true;
                kitsuProgressBar.setVisibility(View.VISIBLE);
                KitsuAnimeSearchTask searchTask = new KitsuAnimeSearchTask(getContext(), kitsuSearchView.getQuery().toString());
                searchTask.execute();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return view;
    }

    private class KitsuAnimeSearchTask extends AsyncTask<Void, Void, ArrayList<Kitsu>> {

        Context context;
        String anime;

        public KitsuAnimeSearchTask(Context context, String anime) {
            this.context = context;
            this.anime = anime;
        }

        @Override
        protected ArrayList<Kitsu> doInBackground(Void... voids) {
            WanPisuConstants.kitsus = new ArrayList<>();
            if (isSearching){
                KitsuAPI.searchAnime(anime);
            }else {
                KitsuAPI.getTrendingAnime();
            }
            return WanPisuConstants.kitsus;
        }

        @Override
        protected void onPostExecute(ArrayList<Kitsu> kitsus) {
            kitsuProgressBar.setVisibility(View.GONE);
            KitsuAnimeAdapter adapter = new KitsuAnimeAdapter(WanPisuConstants.kitsus);
            GridLayoutManager manager = new GridLayoutManager(context, 3);
            kitsuRecyclerView.setLayoutManager(manager);
            kitsuRecyclerView.setAdapter(adapter);
            Log.e("LENGTH", "LENGTH: " + WanPisuConstants.kitsus.size());
        }
    }

}