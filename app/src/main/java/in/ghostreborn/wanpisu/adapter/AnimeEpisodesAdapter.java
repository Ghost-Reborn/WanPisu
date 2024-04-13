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

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.ui.ExoPlayerActivity;

public class AnimeEpisodesAdapter extends RecyclerView.Adapter<AnimeEpisodesAdapter.ViewHolder> {

    Context context;

    public AnimeEpisodesAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.anime_episodes_list, parent, false);
        return new AnimeEpisodesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String episodeNumber = WanPisuConstants.episodes.get(position).getEpisodeNumber();
        if (isInteger(episodeNumber)){
            episodeNumber = Integer.parseInt(episodeNumber) + WanPisuConstants.ALL_ANIME_EPISODE_ADD + "";
        }else {
            episodeNumber = Float.parseFloat(episodeNumber) + WanPisuConstants.ALL_ANIME_EPISODE_ADD + "";
        }

        holder.episodeNumberTextView.setText(episodeNumber);

        int pos = WanPisuConstants.ALL_ANIME_EPISODE_ADD + position;

        String episodeTitle = WanPisuConstants.episodes.get(pos).getEpisodeTitle();
        if (episodeTitle.isEmpty()){
            holder.episodeTitleTextView.setText(String.format("Episode %s", episodeNumber));
        }else {
            holder.episodeTitleTextView.setText(episodeTitle);
        }

        String episodeThumbnail = WanPisuConstants.episodes.get(pos).getEpisodeThumbnail();
        if (episodeThumbnail.isEmpty()){
            Picasso.get().load(WanPisuConstants.animeThumbnail).into(holder.episodeImageView);
        }else {
            Picasso.get().load(episodeThumbnail).into(holder.episodeImageView);
        }

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ExoPlayerActivity.class);
            WanPisuConstants.ALL_ANIME_EPISODE_NUMBER = WanPisuConstants.episodes.get(position).getEpisodeNumber();
            context.startActivity(intent);
        });
    }

    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public int getItemCount() {
        if ((WanPisuConstants.ALL_ANIME_EPISODE_ADD + WanPisuConstants.EPISODE_VISIBLE) < WanPisuConstants.episodes.size()) {
            return WanPisuConstants.EPISODE_VISIBLE;
        } else {
            int start = Integer.parseInt(WanPisuConstants.episodes.get(0).getEpisodeNumber());
            if (start == 0) {
                return (WanPisuConstants.episodes.size() % WanPisuConstants.EPISODE_VISIBLE) - 1;
            }
            return WanPisuConstants.episodes.size() % WanPisuConstants.EPISODE_VISIBLE;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView episodeNumberTextView;
        public TextView episodeTitleTextView;
        public ImageView episodeImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            episodeNumberTextView = itemView.findViewById(R.id.chapter_number_text_view);
            episodeTitleTextView = itemView.findViewById(R.id.chapter_title_text_view);
            episodeImageView = itemView.findViewById(R.id.chapter_image_view);
        }
    }

}
