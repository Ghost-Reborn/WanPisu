package in.ghostreborn.wanpisu.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.model.Kitsu;

public class KitsuAnimeAdapter extends RecyclerView.Adapter<KitsuAnimeAdapter.ViewHolder> {

    ArrayList<Kitsu> kitsus;

    public KitsuAnimeAdapter(ArrayList<Kitsu> kitsus){
        this.kitsus = kitsus;
    }

    @NonNull
    @Override
    public KitsuAnimeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kitsu_anime_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KitsuAnimeAdapter.ViewHolder holder, int position) {
        holder.kitsuAnimeNameTextView.setText(kitsus.get(position).getAnime());
        Picasso.get().load(kitsus.get(position).getThumbnail()).into(
                holder.kitsuAnimeImageView
        );
    }

    @Override
    public int getItemCount() {
        return kitsus.size();
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
