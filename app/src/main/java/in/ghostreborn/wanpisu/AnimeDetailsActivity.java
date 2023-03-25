package in.ghostreborn.wanpisu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

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

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import in.ghostreborn.wanpisu.parser.AllAnime;

public class AnimeDetailsActivity extends AppCompatActivity {

    private PlayerView exoplayerView;

    private SimpleExoPlayer simpleExoPlayer;
    private MediaSource mediaSource;

    private URLType urlType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_details);

        findViews();
        initPlayer();

        Intent intent = getIntent();
        String animeID = intent.getStringExtra("ANIME_ID");


        Executor executor = Executors.newSingleThreadExecutor();
        Runnable task = () -> {
            ArrayList<String> servers = AllAnime.getAnimeServer(animeID);
            StringBuilder server = new StringBuilder();
            for (int i = 0; i < servers.size(); i++) {
                server.append(servers.get(i));
                server.append("\n");
            }
        };
        executor.execute(task);
    }

    private void findViews() {
        exoplayerView = findViewById(R.id.exoplayerView);
    }

    private void initPlayer() {
        simpleExoPlayer = new SimpleExoPlayer.Builder(this).build();
        exoplayerView.setPlayer(simpleExoPlayer);

        createMediaSource();

        simpleExoPlayer.setMediaSource(mediaSource);
        simpleExoPlayer.prepare();
    }

    private void createMediaSource(){
        urlType = URLType.MP4;
        urlType.setUrl("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4");

//        urlType = URLType.HLS
//        urlType.url = "https://demo.unified-streaming.com/k8s/features/stable/video/tears-of-steel/tears-of-steel.ism/.m3u8"

        simpleExoPlayer.seekTo(0);
        if (urlType == URLType.MP4) {
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(
                    this,
                    Util.getUserAgent(this, getApplicationInfo().name)
            );
            mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(Uri.parse(urlType.getUrl())));
        } else if (urlType == URLType.HLS) {
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(
                    this,
                    Util.getUserAgent(this, getApplicationInfo().name)
            );
            mediaSource = new HlsMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(Uri.parse(urlType.getUrl())));
        }
    }

}

