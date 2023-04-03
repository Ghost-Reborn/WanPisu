package in.ghostreborn.wanpisu.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.parser.AllAnime;

public class ExoPlayerActivity extends AppCompatActivity {

    private static PlayerView exoplayerView;

    private static SimpleExoPlayer simpleExoPlayer;
    private static MediaSource mediaSource;

    public static void initPlayer(String url, Context context) {
        simpleExoPlayer = new SimpleExoPlayer.Builder(context).build();
        exoplayerView.setPlayer(simpleExoPlayer);

        createMediaSource(url, context);

        simpleExoPlayer.setMediaSource(mediaSource);
        simpleExoPlayer.prepare();
    }

    private static void createMediaSource(String url, Context context) {

        simpleExoPlayer.seekTo(0);
        boolean isHLS = AllAnime.isHLS;
        if (!isHLS) {
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(
                    context,
                    Util.getUserAgent(context, context.getApplicationInfo().name)
            );
            mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(Uri.parse(url)));
        } else {
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(
                    context,
                    Util.getUserAgent(context, context.getApplicationInfo().name)
            );
            mediaSource = new HlsMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(Uri.parse(url)));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo_player);

        Log.e("EXOPLAYER", WanPisuConstants.preferences.getString(WanPisuConstants.ALL_ANIME_ANIME_ID, ""));

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        Intent intent = getIntent();
        String server = intent.getStringExtra("ANIME_SERVER");
        findViews();
        initPlayer(server, ExoPlayerActivity.this);

    }

    private void findViews() {
        exoplayerView = findViewById(R.id.exoplayerView);
    }

}

