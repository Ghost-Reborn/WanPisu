package in.ghostreborn.wanpisu.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.AnilistAdapter;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.parser.AnilistParser;
import in.ghostreborn.wanpisu.utils.AnilistUtils;

public class AnilistLoginFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anilist_login, container, false);
        Button anilistLoginButton = view.findViewById(R.id.anilist_login_button);
        RecyclerView animeRecyclerView = view.findViewById(R.id.anilist_recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 3);
        animeRecyclerView.setLayoutManager(gridLayoutManager);
        if (!AnilistUtils.checkAnilist(requireContext())) {
            anilistLoginButton.setVisibility(View.VISIBLE);
            anilistLoginButton.setOnClickListener(v -> {
                AnilistUtils.checkAnilist(requireContext());
            });
        } else {
            Executor executor = Executors.newSingleThreadExecutor();
            Runnable task = () -> {
                WanPisuConstants.wanPisus = new ArrayList<>();
                String TOKEN = WanPisuConstants.preferences.getString(WanPisuConstants.WAN_PISU_ANILIST_TOKEN, "");
                String userName = WanPisuConstants.preferences.getString(WanPisuConstants.ANILIST_USER_NAME, "");
                AnilistParser.getAnimeDetails(
                        userName,
                        WanPisuConstants.ANIME_CURRENT,
                        TOKEN
                );
                AnilistAdapter adapter = new AnilistAdapter(requireContext(), getActivity());
                requireActivity().runOnUiThread(() -> {
                    animeRecyclerView.setAdapter(adapter);
                    anilistLoginButton.setVisibility(View.GONE);
                });
            };
            executor.execute(task);
        }
        return view;
    }

}