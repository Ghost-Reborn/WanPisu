package in.ghostreborn.wanpisu.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.AnilistRecyclerAdapter;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.model.Anilist;
import in.ghostreborn.wanpisu.parser.AnilistParser;
import in.ghostreborn.wanpisu.utils.AnilistUtils;

public class AnilistCurrentFragment extends Fragment {

    String ANIME_TYPE;

    public AnilistCurrentFragment(String ANIME_TYPE){
        this.ANIME_TYPE = ANIME_TYPE;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.anilist_fragment, container, false);

        Context context = view.getContext();

        AnilistUtils.checkAnilist(context);

        SharedPreferences preferences = context.getSharedPreferences(WanPisuConstants.WAN_PISU_PREFERENCE, MODE_PRIVATE);
        String TOKEN = preferences.getString(WanPisuConstants.WAN_PISU_ANILIST_TOKEN, "");
        String userName = preferences.getString(WanPisuConstants.ANILIST_USER_NAME, "");

        RecyclerView anilistRecyclerView = view.findViewById(R.id.anilist_recycler_view);
        GridLayoutManager manager = new GridLayoutManager(context, 2);
        anilistRecyclerView.setLayoutManager(manager);

        Executor executor = Executors.newSingleThreadExecutor();
        Runnable task = () -> {
            ArrayList<Anilist> anilists = AnilistParser.getAnimeDetails(userName, ANIME_TYPE, TOKEN);
            AnilistRecyclerAdapter adapter = new AnilistRecyclerAdapter(anilists, getContext());
            getActivity().runOnUiThread(() -> {
                anilistRecyclerView.setAdapter(adapter);
            });
        };
        executor.execute(task);
        return view;
    }
}
