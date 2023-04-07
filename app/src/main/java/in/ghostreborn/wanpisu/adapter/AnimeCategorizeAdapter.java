package in.ghostreborn.wanpisu.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.ui.EpisodesSelectActivity;

public class AnimeCategorizeAdapter extends RecyclerView.Adapter<AnimeCategorizeAdapter.ViewHolder> {

    @NonNull
    @Override
    public AnimeCategorizeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.episode_categorize_list, parent, false);
        return new AnimeCategorizeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeCategorizeAdapter.ViewHolder holder, int position) {
        if (position != (WanPisuConstants.animeOffsets.size() - 2)) {
            holder.startEpisode.setText(Integer.parseInt(WanPisuConstants.animeOffsets.get(position)) + 1 + "");
            holder.endEpisode.setText(
                    Integer.parseInt(WanPisuConstants.animeOffsets.get(position)) + 20 + ""
            );
        } else {
            holder.startEpisode.setText(Integer.parseInt(WanPisuConstants.animeOffsets.get(position)) + 1 + "");
            holder.endEpisode.setText(
                    Integer.parseInt(WanPisuConstants.animeOffsets.get(position + 1)) + ""
            );
        }

        holder.itemView.setOnClickListener(view -> {
            new EpisodesSelectActivity.KitsuEpisodeTask(WanPisuConstants.animeOffsets.get(position)).execute();
        });
    }

    @Override
    public int getItemCount() {
        return WanPisuConstants.animeOffsets.size() - 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView startEpisode;
        TextView endEpisode;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            startEpisode = itemView.findViewById(R.id.anime_categorize_start_text_view);
            endEpisode = itemView.findViewById(R.id.anime_categorize_end_text_view);

        }
    }
}
