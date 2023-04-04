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
import in.ghostreborn.wanpisu.ui.KitsuAnimeActivity;

public class KitsuTrendingAnimeAdapter extends RecyclerView.Adapter<KitsuTrendingAnimeAdapter.ViewHolder>{
    @NonNull
    @Override
    public KitsuTrendingAnimeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kitsu_anime_list, parent, false);
        return new KitsuTrendingAnimeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KitsuTrendingAnimeAdapter.ViewHolder holder, int position) {
        holder.kitsuAnimeNameTextView.setText(WanPisuConstants.kitsus.get(position).getAnime());
        Picasso.get().load(WanPisuConstants.kitsus.get(position).getThumbnail()).into(holder.kitsuAnimeImageView);
        holder.itemView.setOnClickListener(view -> {
            Context context = holder.itemView.getContext();
            Intent intent = new Intent(context, KitsuAnimeActivity.class);
            WanPisuConstants.isUserAnime = false;
            WanPisuConstants.preferences.edit()
                    .putString(WanPisuConstants.KITSU_ANIME_ID,  WanPisuConstants.kitsus.get(position).getAnimeID())
                    .putString(WanPisuConstants.KITSU_ANIME_INDEX,  String.valueOf(position))
                    .apply();
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return WanPisuConstants.kitsus.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView kitsuAnimeNameTextView;
        public ImageView kitsuAnimeImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            kitsuAnimeNameTextView = itemView.findViewById(R.id.kitsu_anime_text_view);
            kitsuAnimeImageView = itemView.findViewById(R.id.kitsu_anime_image_view);
        }
    }
}
