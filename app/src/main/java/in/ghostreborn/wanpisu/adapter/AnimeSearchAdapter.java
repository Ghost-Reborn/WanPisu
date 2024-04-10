package in.ghostreborn.wanpisu.adapter;

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

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.model.WanPisu;
import in.ghostreborn.wanpisu.ui.AnimeEpisodesActivity;

public class AnimeSearchAdapter extends RecyclerView.Adapter<AnimeSearchAdapter.ViewHolder> {

    private static ArrayList<WanPisu> animeNames;
    Context context;

    public AnimeSearchAdapter(Context mContext, ArrayList<WanPisu> mAnimeNames) {
        animeNames = mAnimeNames;
        context = mContext;
    }

    @NonNull
    @Override
    public AnimeSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.anime_search_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeSearchAdapter.ViewHolder holder, int position) {

        holder.animeTextView.setText(animeNames.get(position).getAnimeName());
        Picasso.get().load(animeNames.get(position).getAnimeThumbnailUrl())
                .into(holder.animeImageView);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, AnimeEpisodesActivity.class);
            WanPisuConstants.animeImageURL = animeNames.get(position).getAnimeThumbnailUrl();
            WanPisuConstants.wanPisu = animeNames.get(position);
            WanPisuConstants.ALL_ANIME_ID = animeNames.get(position).getAnimeID();
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return animeNames.size();
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
