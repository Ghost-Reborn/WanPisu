package in.ghostreborn.wanpisu.adapter;

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
        AllManga allManga = WanPisuConstants.allMangas.get(position);
        holder.mangaTextView.setText(allManga.getName());
        Picasso.get().load(allManga.getThumbnail())
                .into(holder.mangaImageView);
    }

    @Override
    public int getItemCount() {
        return WanPisuConstants.allMangas.size();
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
