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

public class KitsuUserAnimeAdapter extends RecyclerView.Adapter<KitsuUserAnimeAdapter.ViewHolder>{

    @NonNull
    @Override
    public KitsuUserAnimeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kitsu_anime_list, parent, false);
        return new KitsuUserAnimeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KitsuUserAnimeAdapter.ViewHolder holder, int position) {
        holder.kitsuAnimeNameTextView.setText(WanPisuConstants.userKitsus.get(position).getAnime());
        Picasso.get().load(WanPisuConstants.userKitsus.get(position).getThumbnail()).into(holder.kitsuAnimeImageView);
        holder.itemView.setOnClickListener(view -> {
            Context context = holder.itemView.getContext();
            Intent intent = new Intent(context, KitsuAnimeActivity.class);
            WanPisuConstants.isUserAnime = true;
            WanPisuConstants.preferences.edit()
                    .putString(WanPisuConstants.KITSU_ANIME_ID,  WanPisuConstants.userKitsus.get(position).getAnimeID())
                    .putString(WanPisuConstants.KITSU_ANIME_INDEX,  String.valueOf(position))
                    .apply();
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return WanPisuConstants.userKitsus.size();
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
