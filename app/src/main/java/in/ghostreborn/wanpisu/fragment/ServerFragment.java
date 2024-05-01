package in.ghostreborn.wanpisu.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.ServersAdapter;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.parser.AllAnimeParser;

public class ServerFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_server, container, false);

        RecyclerView serverRecyclerView = view.findViewById(R.id.server_recycler_view);
        Executor executor = Executors.newSingleThreadExecutor();
        Runnable task = () -> {
            AllAnimeParser.getEpisodeServers(WanPisuConstants.ALL_ANIME_ID, WanPisuConstants.ALL_ANIME_EPISODE_NUMBER);
            requireActivity().runOnUiThread(() -> {
                ServersAdapter adapter = new ServersAdapter(requireActivity());
                LinearLayoutManager manager = new LinearLayoutManager(requireContext());
                serverRecyclerView.setLayoutManager(manager);
                serverRecyclerView.setAdapter(adapter);
            });
        };
        executor.execute(task);

        return view;
    }
}