package in.ghostreborn.wanpisu.adapter;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.database.AnilistDatabaseHelper;
import in.ghostreborn.wanpisu.model.Anilist;
import in.ghostreborn.wanpisu.parser.AllAnime;
import in.ghostreborn.wanpisu.ui.AnimeEpisodesActivity;

public class AnilistAdapter extends RecyclerView.Adapter<AnilistAdapter.ViewHolder> {

    Context context;
    Activity activity;
    ProgressBar anilistProgressBar;

    public AnilistAdapter(Context mContext, Activity activity, ProgressBar anilistProgressBar) {
        context = mContext;
        this.activity = activity;
        this.anilistProgressBar = anilistProgressBar;
    }

    @NonNull
    @Override
    public AnilistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.anime_search_list, parent, false);
        return new AnilistAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnilistAdapter.ViewHolder holder, int position) {

        Anilist anilist = WanPisuConstants.anilists.get(position);
        holder.animeTextView.setText(anilist.getAnimeName());
        Picasso.get().load(anilist.getAnimeImageUrl())
                .into(holder.animeImageView);
        holder.itemView.setOnClickListener(v -> {
            WanPisuConstants.isAnilist = true;
            if (!anilist.getAllAnimeID().isEmpty()){
                WanPisuConstants.ALL_ANIME_ID = anilist.getAllAnimeID();
                Intent intent = new Intent(context, AnimeEpisodesActivity.class);
                context.startActivity(intent);
            }else {
                anilistProgressBar.setVisibility(View.VISIBLE);
                Executor executor = Executors.newSingleThreadExecutor();
                Runnable task = () -> {
                    String allAnimeID = AllAnime.getAllAnimeID(anilist.getAnimeName(), anilist.getMalID());
                    activity.runOnUiThread(() -> {
                        saveAllAnimeID(context,position,allAnimeID,anilist.getMalID());
                        WanPisuConstants.animeImageURL = anilist.getAnimeImageUrl();
                        WanPisuConstants.ALL_ANIME_ID = allAnimeID;
                        anilistProgressBar.setVisibility(View.GONE);
                        Intent intent = new Intent(context, AnimeEpisodesActivity.class);
                        context.startActivity(intent);
                    });
                };
                executor.execute(task);
            }
        });
    }

    @Override
    public int getItemCount() {
        return WanPisuConstants.anilists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView animeTextView;
        public ImageView animeImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            animeTextView = itemView.findViewById(R.id.anime_text_view);
            animeImageView = itemView.findViewById(R.id.anime_image_view);
        }
    }

    private void saveAllAnimeID(Context context, int pos, String allAnimeID, String malID){
        AnilistDatabaseHelper helper = new AnilistDatabaseHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(WanPisuConstants.COLUMN_ALL_ANIME_ID, allAnimeID);
        long test = db.update(WanPisuConstants.TABLE_NAME, contentValues, WanPisuConstants.COLUMN_ANIME_MAL_ID + "=?", new String[]{malID});
        Log.e("TAG", "Updated at: " + test);
    }

}
