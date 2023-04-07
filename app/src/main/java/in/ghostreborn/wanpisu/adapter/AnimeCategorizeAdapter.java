package in.ghostreborn.wanpisu.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.ui.EpisodesSelectActivity;

public class AnimeCategorizeAdapter extends RecyclerView.Adapter<AnimeCategorizeAdapter.ViewHolder>{

    int startEpisode = 1;
    int endEpisode, total;
    int OFFSET = 0;
    public AnimeCategorizeAdapter(String endEpisode){
        this.endEpisode = Integer.parseInt(endEpisode);
        this.total = this.endEpisode / 20;
    }


    @NonNull
    @Override
    public AnimeCategorizeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.episode_categorize_list, parent, false);
        return new AnimeCategorizeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeCategorizeAdapter.ViewHolder holder, int position) {
        holder.startEpisode.setText(String.valueOf(startEpisode));
        holder.endEpisode.setText(String.valueOf(startEpisode + 19));
        startEpisode = startEpisode + 20;
        holder.itemView.setOnClickListener(view -> {
            new EpisodesSelectActivity.KitsuEpisodeTask(String.valueOf(OFFSET)).execute();
        });
        OFFSET += 20;
    }

    @Override
    public int getItemCount() {
        return total;
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
