package in.ghostreborn.wanpisu.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.parser.AllAnimeParser;

public class TestFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);

        TextView testText = view.findViewById(R.id.test_text);

        Executor executor = Executors.newSingleThreadExecutor();
        Runnable task = () -> {
            String test = AllAnimeParser.getEpisodeServers("ReooPAxPMsHM4KPMY", "25");
            requireActivity().runOnUiThread(() -> {
                testText.setText(test);
            });
        };
        executor.execute(task);

        return view;
    }
}