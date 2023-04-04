package in.ghostreborn.wanpisu.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.ui.ExoPlayerActivity;

public class AnimeServersAdapter extends RecyclerView.Adapter<AnimeServersAdapter.ViewHolder> {

    Context context;
    ArrayList<String> servers;

    public AnimeServersAdapter(Context context, ArrayList<String> servers) {
        this.context = context;
        this.servers = servers;
    }

    @NonNull
    @Override
    public AnimeServersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.anime_server_list, parent, false);
        return new AnimeServersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        int mPosition = position;

        holder.animeServerSelectTextView.setText(
                servers.get(mPosition)
        );
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ExoPlayerActivity.class);
                WanPisuConstants.preferences.edit()
                        .putString(WanPisuConstants.ALL_ANIME_ANIME_SERVER,servers.get(mPosition))
                        .apply();
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return servers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView animeServerSelectTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            animeServerSelectTextView = itemView.findViewById(R.id.anime_server_select_text_view);
        }
    }
}
