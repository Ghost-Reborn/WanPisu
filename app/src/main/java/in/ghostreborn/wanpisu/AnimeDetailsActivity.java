package in.ghostreborn.wanpisu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

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
        initPlayer("https://workfields.backup-server222.lol/7d2473746a243c24296267726734296b63626f67353e36293e4e48677e5c735733635c73653544516e29757364293759323e3676286b7632242a2475727463676b63744f62243c245f722b5542247b");

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
            Log.e("ANIME", server.toString());
        };
        executor.execute(task);
    }

    private void findViews() {
        exoplayerView = findViewById(R.id.exoplayerView);
    }

    private void initPlayer(String url) {
        simpleExoPlayer = new SimpleExoPlayer.Builder(this).build();
        exoplayerView.setPlayer(simpleExoPlayer);

        createMediaSource(url);

        simpleExoPlayer.setMediaSource(mediaSource);
        simpleExoPlayer.prepare();
    }

    private void createMediaSource(String url){
        urlType = URLType.MP4;
        urlType.setUrl(url);

//        urlType = URLType.HLS;
//        urlType.setUrl(url);

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

