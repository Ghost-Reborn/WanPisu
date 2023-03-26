package in.ghostreborn.wanpisu.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.parser.AllAnime;

public class ExoPlayerActivity extends AppCompatActivity {

    private static PlayerView exoplayerView;

    private static SimpleExoPlayer simpleExoPlayer;
    private static MediaSource mediaSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo_player);

        Intent intent = getIntent();
        String animeID = intent.getStringExtra("ANIME_ID");
        int episodeNumber = intent.getIntExtra("ANIME_EPISODE_NUMBER", 1);
        findViews();

        AnimeAsync animeAsync = new AnimeAsync(
                animeID,
                String.valueOf(episodeNumber)
        );
        animeAsync.execute();

    }

    private void findViews() {
        exoplayerView = findViewById(R.id.exoplayerView);
    }

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

    class AnimeAsync extends AsyncTask<String, Void, String> {

        String animeID;
        String episodeNumber;

        public AnimeAsync(String mAnimeID, String mEpisodeNumber) {
            animeID = mAnimeID;
            episodeNumber = mEpisodeNumber;
        }

        @Override
        protected String doInBackground(String... strings) {
            return AllAnime.getAnimeServer(animeID, episodeNumber);
        }

        @Override
        protected void onPostExecute(String strings) {
            super.onPostExecute(strings);

            String server = strings;
            ExoPlayerActivity.initPlayer(server, ExoPlayerActivity.this);

        }
    }

}

