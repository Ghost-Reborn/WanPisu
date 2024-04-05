package in.ghostreborn.wanpisu.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.AnimeSearchAdapter;
import in.ghostreborn.wanpisu.adapter.MangaSearchAdapter;
import in.ghostreborn.wanpisu.model.AllManga;
import in.ghostreborn.wanpisu.model.Manga;
import in.ghostreborn.wanpisu.model.WanPisu;
import in.ghostreborn.wanpisu.parser.AllAnime;
import in.ghostreborn.wanpisu.parser.MangaParser;

public class TestFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        RecyclerView mangaContainerView = view.findViewById(R.id.manga_container);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 3);
        mangaContainerView.setLayoutManager(gridLayoutManager);

        Executor executor = Executors.newSingleThreadExecutor();
        Runnable task = () -> {
            MangaParser.searchManga("");
            MangaSearchAdapter adapter = new MangaSearchAdapter();
            requireActivity().runOnUiThread(() -> mangaContainerView.setAdapter(adapter));
        };
        executor.execute(task);

        // Search anime
        SearchView mangaSearchView = view.findViewById(R.id.manga_search_view);
        mangaSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Executor executor = Executors.newSingleThreadExecutor();
                Runnable task = () -> {
                    String mangaName = mangaSearchView.getQuery().toString();
                    MangaParser.searchManga(mangaName);
                    MangaSearchAdapter adapter = new MangaSearchAdapter();
                    requireActivity().runOnUiThread(() -> mangaContainerView.setAdapter(adapter));
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