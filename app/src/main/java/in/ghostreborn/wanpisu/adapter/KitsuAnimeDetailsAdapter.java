package in.ghostreborn.wanpisu.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.ui.WanPisuActivity;

public class KitsuAnimeDetailsAdapter extends RecyclerView.Adapter<KitsuAnimeDetailsAdapter.ViewHolder>{
    @NonNull
    @Override
    public KitsuAnimeDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kitsu_anime_details_list  , parent, false);
        return new KitsuAnimeDetailsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KitsuAnimeDetailsAdapter.ViewHolder holder, int position) {
        holder.kitsuDetailTextView.setText(WanPisuConstants.kitsuDetails.get(position).getAnime());
        holder.kitsuDetailDescriptionView.setText(WanPisuConstants.kitsuDetails.get(position).getDescription());
        holder.kitsuDetailEpisodesView.setText(WanPisuConstants.kitsuDetails.get(position).getEpisodes());
        holder.kitsuDetailRatingView.setText(WanPisuConstants.kitsuDetails.get(position).getAverageRating());
        holder.kitsuDetailWatchButton.setOnClickListener(view -> {
            Context context = holder.itemView.getContext();
            Intent watchIntent = new Intent(context, WanPisuActivity.class);
            watchIntent.putExtra("ANIME_NAME", holder.kitsuDetailTextView.getText().toString());
            context.startActivity(watchIntent);
        });
        Picasso.get().load(WanPisuConstants.kitsuDetails.get(position).getThumbnail()).into(holder.kitsuDetailImageView);
    }

    @Override
    public int getItemCount() {
        return WanPisuConstants.kitsuDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView kitsuDetailTextView;
        public ImageView kitsuDetailImageView;
        public TextView kitsuDetailDescriptionView;
        public TextView kitsuDetailEpisodesView;
        public TextView kitsuDetailRatingView;
        public Button kitsuDetailWatchButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            kitsuDetailTextView = itemView.findViewById(R.id.kitsu_detail_text_view);
            kitsuDetailImageView = itemView.findViewById(R.id.kitsu_detail_image_view);
            kitsuDetailDescriptionView = itemView.findViewById(R.id.kitsu_detail_description_view);
            kitsuDetailEpisodesView = itemView.findViewById(R.id.kitsu_detail_episodes_view);
            kitsuDetailRatingView = itemView.findViewById(R.id.kitsu_detail_rating_view);
            kitsuDetailWatchButton = itemView.findViewById(R.id.kitsu_detail_watch_button);
        }
    }
}
