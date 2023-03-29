package in.ghostreborn.wanpisu.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.R;

public class AnilistAdapter extends RecyclerView.Adapter<AnilistAdapter.ViewHolder> {

    ArrayList<String> anilists;

    public AnilistAdapter(ArrayList<String> anilists){
        this.anilists = anilists;
    }

    @NonNull
    @Override
    public AnilistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(in.ghostreborn.wanpisu.R.layout.anilist_anime_list, parent, false);
        return new AnilistAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnilistAdapter.ViewHolder holder, int position) {
        holder.anilistAnimeTextView.setText(anilists.get(position));
    }

    @Override
    public int getItemCount() {
        return anilists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView anilistAnimeTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            anilistAnimeTextView = itemView.findViewById(R.id.anilist_anime_text_view);
        }
    }
}
