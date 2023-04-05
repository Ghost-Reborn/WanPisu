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

import in.ghostreborn.wanpisu.AnimeDownloaderActivity;
import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;

public class AnimeSearchAdapter extends RecyclerView.Adapter<AnimeSearchAdapter.ViewHolder> {

    private static Context context;

    public AnimeSearchAdapter(Context mContext) {
        context = mContext;
    }

    private static View.OnClickListener listener(int position){
        return view -> {
            Intent intent = new Intent(context, AnimeDownloaderActivity.class);
            String animeID = WanPisuConstants.animeNames.get(position).getAnimeID();
            WanPisuConstants.preferences.edit()
                            .putString(WanPisuConstants.ALL_ANIME_ANIME_ID, animeID)
                            .putString(WanPisuConstants.ALL_ANIME_ANIME_EPISODES, String.valueOf(WanPisuConstants.animeNames.get(position).getTotalEpisodes()))
                    .apply();
            context.startActivity(intent);
        };
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

        String totalEpisodes = "Episodes: " + WanPisuConstants.animeNames.get(position).getTotalEpisodes();

        holder.animeTextView.setText(WanPisuConstants.animeNames.get(position).getAnimeName());
        holder.totalEpisodesTextView.setText(totalEpisodes);
        Picasso.get().load(WanPisuConstants.animeNames.get(position).getAnimeThumbnailUrl())
                .into(holder.animeImageView);
        holder.itemView.setOnClickListener(listener(position));
    }

    @Override
    public int getItemCount() {
        return WanPisuConstants.animeNames.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView animeTextView;
        public ImageView animeImageView;
        public TextView totalEpisodesTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            animeTextView = itemView.findViewById(R.id.anime_text_view);
            animeImageView = itemView.findViewById(R.id.anime_image_view);
            totalEpisodesTextView = itemView.findViewById(R.id.total_episodes_text_view);
        }
    }
}
