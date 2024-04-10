package in.ghostreborn.wanpisu.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

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

import java.util.ArrayList;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.parser.AllAnime;

public class ExoPlayerActivity extends AppCompatActivity {

    private static PlayerView exoplayerView;

    private static SimpleExoPlayer simpleExoPlayer;
    private static MediaSource mediaSource;
    ProgressBar exoPlayerProgressBar;

    public static void initPlayer(String url, Context context) {
        simpleExoPlayer = new SimpleExoPlayer.Builder(context).build();
        exoplayerView.setPlayer(simpleExoPlayer);

        createMediaSource(url, context);

        simpleExoPlayer.setMediaSource(mediaSource);
        simpleExoPlayer.prepare();
    }

    private static void createMediaSource(String url, Context context) {

        simpleExoPlayer.seekTo(0);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo_player);

        exoPlayerProgressBar = findViewById(R.id.exoplayer_progress_bar);



        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

    }

    private void findViews() {
        exoplayerView = findViewById(R.id.exoplayerView);
    }

    class AnimeAsync extends AsyncTask<String, Void, ArrayList<String>> {

        String animeID;
        String episodeNumber;

        public AnimeAsync(String mAnimeID, String mEpisodeNumber) {
            animeID = mAnimeID;
            episodeNumber = mEpisodeNumber;
        }

        @Override
        protected ArrayList<String> doInBackground(String... strings) {

            return AllAnime.getAnimeServer(animeID, episodeNumber);
        }

        @Override
        protected void onPostExecute(ArrayList<String> servers) {
            super.onPostExecute(servers);

            String server = "";
            for (int i = 0; i < servers.size(); i++) {
                String currentServer = servers.get(i).trim();
                if (currentServer.contains("workfields")) ;
                {
                    server = servers.get(i);
                    break;
                }
            }
            Log.e("SERVER: ", "SERVER: " + server);
            findViews();
            exoPlayerProgressBar.setVisibility(View.GONE);
            initPlayer(server, ExoPlayerActivity.this);

        }
    }

}

