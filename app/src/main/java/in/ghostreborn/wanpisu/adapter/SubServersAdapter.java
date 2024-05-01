package in.ghostreborn.wanpisu.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;

public class SubServersAdapter extends RecyclerView.Adapter<SubServersAdapter.ViewHolder> {

    Activity activity;
    public SubServersAdapter(Activity activity){
        this.activity = activity;
    }

    @NonNull
    @Override
    public SubServersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.anime_sub_server_list, parent, false);
        return new SubServersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubServersAdapter.ViewHolder holder, int position) {

        String serverName = WanPisuConstants.subServers.get(position).getServerName();
        String serverUrl = WanPisuConstants.subServers.get(position).getServerUrl();

        holder.animeServerTextView.setText(serverName);
        holder.animeServerTextView.setOnClickListener(v -> {
            Toast.makeText(activity, serverUrl, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return WanPisuConstants.subServers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView animeServerTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            animeServerTextView = itemView.findViewById(R.id.anime_sub_server_select_text_view);
        }
    }

}
