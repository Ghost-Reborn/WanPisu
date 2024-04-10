package in.ghostreborn.wanpisu.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.concurrent.ConcurrentHashMap;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.model.AllManga;
import in.ghostreborn.wanpisu.ui.MangaActivity;

public class MangaSearchAdapter extends RecyclerView.Adapter<MangaSearchAdapter.ViewHolder> {

    @NonNull
    @Override
    public MangaSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.manga_search_list, parent, false);
        return new MangaSearchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MangaSearchAdapter.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mangaTextView;
        public ImageView mangaImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mangaTextView = itemView.findViewById(R.id.manga_text_view);
            mangaImageView = itemView.findViewById(R.id.manga_image_view);
        }
    }

}
