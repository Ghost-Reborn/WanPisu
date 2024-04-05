package in.ghostreborn.wanpisu.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.model.AllManga;
import in.ghostreborn.wanpisu.model.Manga;
import in.ghostreborn.wanpisu.parser.MangaParser;

public class TestFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        TextView testText = view.findViewById(R.id.test_text);

        Executor executor = Executors.newSingleThreadExecutor();
        Runnable task = () -> {
            ArrayList<AllManga> allMangas = MangaParser.searchManga("One Piece");
            requireActivity().runOnUiThread(() -> {
                for (int i=0; i< allMangas.size(); i++){
                    AllManga allManga = allMangas.get(i);
                    testText.append("\n\nName: " + allManga.getName());
                    testText.append("\nID: " + allManga.getId());
                    testText.append("\nThumbnail: " + allManga.getThumbnail());
                }
            });
        };
        executor.execute(task);

        return view;
    }

}