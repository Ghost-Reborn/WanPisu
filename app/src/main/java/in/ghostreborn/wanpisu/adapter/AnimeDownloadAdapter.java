package in.ghostreborn.wanpisu.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.model.AnimeDown;

public class AnimeDownloadAdapter extends RecyclerView.Adapter<AnimeDownloadAdapter.ViewHolder> {

    ArrayList<AnimeDown> animeDowns;

    public AnimeDownloadAdapter(ArrayList<AnimeDown> animeDowns) {
        this.animeDowns = animeDowns;
    }

    @NonNull
    @Override
    public AnimeDownloadAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.anime_download_list, parent, false);
        return new AnimeDownloadAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeDownloadAdapter.ViewHolder holder, int position) {
        holder.animeDownloadName.setText(animeDowns.get(position).getAnimeName());
        holder.animeDownloadProgress.setProgress(animeDowns.get(position).getProgress());
    }

    @Override
    public int getItemCount() {
        return animeDowns.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView animeDownloadName;
        public ProgressBar animeDownloadProgress;

        public ViewHolder(View itemView) {
            super(itemView);
            animeDownloadName = itemView.findViewById(R.id.anime_download_name);
            animeDownloadProgress = itemView.findViewById(R.id.anime_download_progress);
        }
    }

}