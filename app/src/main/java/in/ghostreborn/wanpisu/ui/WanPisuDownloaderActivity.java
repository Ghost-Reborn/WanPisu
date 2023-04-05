package in.ghostreborn.wanpisu.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.AnimeDownloadAdapter;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.manager.WanPisuDownloadManager;
import in.ghostreborn.wanpisu.model.AnimeDown;

public class WanPisuDownloaderActivity extends AppCompatActivity {

    RecyclerView wanPisuDownloaderRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wan_pisu_downloader);

        wanPisuDownloaderRecycler = findViewById(R.id.wanpisu_downloader_recycler);
        new WanPisuDownloadAsync().execute();

    }

    @Override
    protected void onResume() {
        super.onResume();

        LinearLayoutManager manager = new LinearLayoutManager(this);
        wanPisuDownloaderRecycler.setLayoutManager(manager);

        Handler mHandler = new Handler();
        Runnable mRunnable = new Runnable() {
            @Override
            public void run() {
                AnimeDownloadAdapter adapter = new AnimeDownloadAdapter(WanPisuConstants.animeDowns);
                wanPisuDownloaderRecycler.setAdapter(adapter);
                // Schedule the task to run again in 1 second
                mHandler.postDelayed(this, 1000);
            }
        };
        mRunnable.run();

    }

    class WanPisuDownloadAsync extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            WanPisuDownloadManager downloadManager = new WanPisuDownloadManager();
            for (int i=0;i<WanPisuConstants.animeDowns.size();i++){
                AnimeDown animeDowns = WanPisuConstants.animeDowns.get(i);
                String fileNameDestination = WanPisuConstants.wanPisuFolder.getAbsolutePath()
                        + animeDowns.getAnimeName()
                        + " - "
                        + animeDowns.getEpisode()
                        + ".mp4";

                try {
                    downloadManager.download(animeDowns.getServer(), fileNameDestination, (bytesRead, contentLength, done) -> {
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(() -> {
                            double progress = ((double) bytesRead / contentLength) * 100;
                            WanPisuConstants.animeDowns.get(WanPisuConstants.animeDowns.indexOf(animeDowns)).setProgress((int)progress);
                        });
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

}