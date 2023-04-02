package in.ghostreborn.wanpisu.adapter;

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

public class KitsuAnimeDetailsAdapter extends RecyclerView.Adapter<KitsuAnimeDetailsAdapter.ViewHolder>{
    @NonNull
    @Override
    public KitsuAnimeDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kitsu_anime_details_list  , parent, false);
        return new KitsuAnimeDetailsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KitsuAnimeDetailsAdapter.ViewHolder holder, int position) {
        holder.kitsuDetailTextView.setText(WanPisuConstants.kitsuDetails.get(position).getAnime());
        Picasso.get().load(WanPisuConstants.kitsuDetails.get(position).getThumbnail()).into(holder.kitsuDetailImageView);
    }

    @Override
    public int getItemCount() {
        return WanPisuConstants.kitsuDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView kitsuDetailTextView;
        public ImageView kitsuDetailImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            kitsuDetailTextView = itemView.findViewById(R.id.kitsu_detail_text_view);
            kitsuDetailImageView = itemView.findViewById(R.id.kitsu_detail_image_view);
        }
    }
}
