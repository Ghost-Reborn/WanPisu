package in.ghostreborn.wanpisu.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.parser.AllAnime;
import in.ghostreborn.wanpisu.parser.TestParser;

public class TestFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);

        TextView testText = view.findViewById(R.id.test_text);

        Executor executor = Executors.newSingleThreadExecutor();
        Runnable task = () -> {
            String test = TestParser.getAllAnimeID("One Piece", "ReooPAxPMsHM4KPMY");
            requireActivity().runOnUiThread(() -> {
                testText.setText(test);
            });
        };
        executor.execute(task);

        return view;
    }
}