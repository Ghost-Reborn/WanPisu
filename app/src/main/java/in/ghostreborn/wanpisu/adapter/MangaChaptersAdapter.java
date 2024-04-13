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
import in.ghostreborn.wanpisu.ui.MangaActivity;
import in.ghostreborn.wanpisu.ui.MangaChaptersActivity;

public class MangaChaptersAdapter extends RecyclerView.Adapter<MangaChaptersAdapter.ViewHolder> {

    @NonNull
    @Override
    public MangaChaptersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.manga_chapters_list, parent, false);
        return new MangaChaptersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MangaChaptersAdapter.ViewHolder holder, int position) {
        String chapter = WanPisuConstants.mangaChapters.get(position);
        holder.mangaChapterNumberTextView.setText(chapter);
        holder.mangaTitleTextView.setText("Chapter " + chapter);
        Context context = holder.itemView.getContext();
        holder.itemView.setOnClickListener(v -> {
            WanPisuConstants.MANGA_CHAPTER = chapter;
            context.startActivity(new Intent(
                    context,
                    MangaActivity.class
            ));
        });
    }

    @Override
    public int getItemCount() {
        return WanPisuConstants.mangaChapters.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mangaTitleTextView;
        public TextView mangaChapterNumberTextView;
        public ImageView mangaImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mangaTitleTextView = itemView.findViewById(R.id.chapter_title_text_view);
            mangaChapterNumberTextView = itemView.findViewById(R.id.chapter_number_text_view);
            mangaImageView = itemView.findViewById(R.id.chapter_image_view);
        }
    }

}