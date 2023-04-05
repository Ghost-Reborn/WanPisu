package in.ghostreborn.wanpisu.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;

public class AnimeDownloaderAdapter extends RecyclerView.Adapter<AnimeDownloaderAdapter.ViewHolder>{

    @NonNull
    @Override
    public AnimeDownloaderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.anime_download_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeDownloaderAdapter.ViewHolder holder, int position) {
        holder.animeDownloadTextView.setText(WanPisuConstants.animeNames.get(position).getAnimeName());
    }

    @Override
    public int getItemCount() {
        return WanPisuConstants.animeNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView animeDownloadTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            animeDownloadTextView = itemView.findViewById(R.id.anime_download_text_view);
        }
    }
}
