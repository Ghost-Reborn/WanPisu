package in.ghostreborn.wanpisu.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.model.Anilist;
import in.ghostreborn.wanpisu.model.WanPisu;
import in.ghostreborn.wanpisu.parser.AllAnime;
import in.ghostreborn.wanpisu.parser.AnilistParser;
import in.ghostreborn.wanpisu.ui.AnimeEpisodesActivity;

public class AnilistAdapter extends RecyclerView.Adapter<AnilistAdapter.ViewHolder> {

    Context context;
    RecyclerView animeRecyclerView;
    Activity activity;

    public AnilistAdapter(Context mContext, RecyclerView animeRecyclerView, Activity activity) {
        context = mContext;
        this.animeRecyclerView = animeRecyclerView;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AnilistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.anime_search_list, parent, false);
        return new AnilistAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnilistAdapter.ViewHolder holder, int position) {

        Anilist anilist = WanPisuConstants.anilists.get(position);
        holder.animeTextView.setText(anilist.getAnimeName());
        Picasso.get().load(anilist.getAnimeImageUrl())
                .into(holder.animeImageView);
        holder.itemView.setOnClickListener(v -> {
            if (!anilist.getAllAnimeID().isEmpty()){
                Intent intent = new Intent(context, AnimeEpisodesActivity.class);
                context.startActivity(intent);
            }else {
                Executor executor = Executors.newSingleThreadExecutor();
                Runnable task = () -> {
                    ArrayList<WanPisu> animeDetailsArray = AllAnime.parseAnimeIDAnimeNameAnimeThumbnail(
                            anilist.getAnimeName()
                    );
                    AnimeSearchAdapter adapter = new AnimeSearchAdapter(holder.itemView.getContext(), animeDetailsArray);
                    activity.runOnUiThread(() -> {
                        animeRecyclerView.setAdapter(adapter);
                    });
                };
                executor.execute(task);
            }
        });
    }

    @Override
    public int getItemCount() {
        return WanPisuConstants.anilists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView animeTextView;
        public ImageView animeImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            animeTextView = itemView.findViewById(R.id.anime_text_view);
            animeImageView = itemView.findViewById(R.id.anime_image_view);
        }
    }
}
