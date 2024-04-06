package in.ghostreborn.wanpisu.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.AnimeSearchAdapter;
import in.ghostreborn.wanpisu.model.WanPisu;
import in.ghostreborn.wanpisu.parser.AllAnime;

public class AnimeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anime, container, false);
        RecyclerView animeContainerView = view.findViewById(R.id.manga_container);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 3);
        animeContainerView.setLayoutManager(gridLayoutManager);

        Executor executor = Executors.newSingleThreadExecutor();
        Runnable task = () -> {
            ArrayList<WanPisu> animeDetailsArray = AllAnime.parseAnimeIDAnimeNameAnimeThumbnail("");
            AnimeSearchAdapter adapter = new AnimeSearchAdapter(view.getContext(), animeDetailsArray);
            requireActivity().runOnUiThread(() -> animeContainerView.setAdapter(adapter));
        };
        executor.execute(task);

        // Search anime
        SearchView animeSearchView = view.findViewById(R.id.manga_search_view);
        animeSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Executor executor = Executors.newSingleThreadExecutor();
                Runnable task = () -> {
                    String animeName = animeSearchView.getQuery().toString();
                    ArrayList<WanPisu> animeDetailsArray = AllAnime.parseAnimeIDAnimeNameAnimeThumbnail(animeName);
                    AnimeSearchAdapter adapter = new AnimeSearchAdapter(view.getContext(), animeDetailsArray);
                    requireActivity().runOnUiThread(() -> animeContainerView.setAdapter(adapter));
                };
                executor.execute(task);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return view;
    }
}