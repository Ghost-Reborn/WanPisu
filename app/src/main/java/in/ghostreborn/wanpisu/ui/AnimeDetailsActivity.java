package in.ghostreborn.wanpisu.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.async.AnimeAsync;

public class AnimeDetailsActivity extends AppCompatActivity {

    private static PlayerView exoplayerView;
    private static SimpleExoPlayer simpleExoPlayer;
    private static MediaSource mediaSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_details);

        Intent intent = getIntent();
        String animeID = intent.getStringExtra("ANIME_ID");
        findViews();

        AnimeAsync animeAsync = new AnimeAsync(animeID, this);
        animeAsync.execute();

    }

    private void findViews() {
        exoplayerView = findViewById(R.id.exoplayerView);
    }

    private static void createMediaSource(String url, Context context) {

        simpleExoPlayer.seekTo(0);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(
                context,
                Util.getUserAgent(context, context.getApplicationInfo().name)
        );
        mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(Uri.parse(url)));
    }

    public static void initPlayer(String url, Context context) {
        simpleExoPlayer = new SimpleExoPlayer.Builder(context).build();
        exoplayerView.setPlayer(simpleExoPlayer);

        createMediaSource(url, context);

        simpleExoPlayer.setMediaSource(mediaSource);
        simpleExoPlayer.prepare();
    }

}

