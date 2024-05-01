package in.ghostreborn.wanpisu.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.parser.ServerParser.DefaultParser;
import in.ghostreborn.wanpisu.parser.ServerParser.LufMp4Parser;
import in.ghostreborn.wanpisu.parser.ServerParser.SMp4Parser;
import in.ghostreborn.wanpisu.parser.ServerParser.SakParser;
import in.ghostreborn.wanpisu.parser.ServerParser.UvMp4Parser;

public class ServersAdapter extends RecyclerView.Adapter<ServersAdapter.ViewHolder> {

    Activity activity;
    public ServersAdapter(Activity activity){
        this.activity = activity;
    }

    @NonNull
    @Override
    public ServersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.anime_server_list, parent, false);
        return new ServersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServersAdapter.ViewHolder holder, int position) {

        String serverName = WanPisuConstants.servers.get(position).getServerName();
        String serverUrl = WanPisuConstants.servers.get(position).getServerUrl();

        holder.animeServerTextView.setText(serverName);
        holder.animeServerTextView.setOnClickListener(v -> {
            Executor executor = Executors.newSingleThreadExecutor();
            Runnable task = () -> {
                if (serverName.equals(WanPisuConstants.SERVER_DEFAULT)){
                    DefaultParser.parseDefault(serverUrl);
                }else if (serverName.equals(WanPisuConstants.SERVER_LUF_MP4)){
                    LufMp4Parser.parseLufMp4(serverUrl);
                }else if (serverName.equals(WanPisuConstants.SERVER_SAK)){
                    SakParser.parseSak(serverUrl);
                }else if (serverName.equals(WanPisuConstants.SERVER_S_MP4)){
                    SMp4Parser.parseSMp4(serverUrl);
                }else if (serverName.equals(WanPisuConstants.SERVER_UV_MP4)){
                    UvMp4Parser.parseUvMp4(serverUrl);
                }else {
                    return;
                }
                activity.runOnUiThread(() -> {
                    SubServersAdapter adapter = new SubServersAdapter(activity);
                    LinearLayoutManager manager = new LinearLayoutManager(activity);
                    holder.animeServerRecyclerView.setLayoutManager(manager);
                    holder.animeServerRecyclerView.setAdapter(adapter);
                });
            };
            executor.execute(task);
        });
    }

    @Override
    public int getItemCount() {
        return WanPisuConstants.servers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView animeServerTextView;
        public RecyclerView animeServerRecyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            animeServerTextView = itemView.findViewById(R.id.anime_sub_server_select_text_view);
            animeServerRecyclerView = itemView.findViewById(R.id.anime_server_recycler_view);
        }
    }

}
