package in.ghostreborn.wanpisu.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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

    public static boolean hasNext = false;
    public static String nextURL = "";
    RecyclerView kitsuRecyclerView;
    ProgressBar kitsuProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kitsu, container, false);
        kitsuRecyclerView = view.findViewById(R.id.kitsu_recycler_view);
        kitsuProgressBar = view.findViewById(R.id.kitsu_recycler_progress);

        KitsuAnimeSearchTask searchTask = new KitsuAnimeSearchTask(view.getContext());
        searchTask.execute();

        return view;
    }

    private class KitsuAnimeSearchTask extends AsyncTask<Void, Void, ArrayList<Kitsu>> {

        Context context;

        public KitsuAnimeSearchTask(Context context) {
            this.context = context;
        }

        @Override
        protected ArrayList<Kitsu> doInBackground(Void... voids) {
            WanPisuConstants.kitsus = new ArrayList<>();
            KitsuAPI.getTrendingAnime();
            return WanPisuConstants.kitsus;
        }

        @Override
        protected void onPostExecute(ArrayList<Kitsu> kitsus) {
            kitsuProgressBar.setVisibility(View.GONE);
            KitsuAnimeAdapter adapter = new KitsuAnimeAdapter();
            GridLayoutManager manager = new GridLayoutManager(context, 3);
            kitsuRecyclerView.setLayoutManager(manager);
            kitsuRecyclerView.setAdapter(adapter);
        }
    }

}