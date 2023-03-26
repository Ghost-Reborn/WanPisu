package in.ghostreborn.wanpisu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.R;

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
        holder.animeServerSelectTextView.setText(
                servers.get(position)
        );
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
