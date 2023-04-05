package in.ghostreborn.wanpisu.ui;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.AnimeDownloadAdapter;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;

public class WanPisuDownloaderActivity extends AppCompatActivity {

    RecyclerView wanPisuDownloaderRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wan_pisu_downloader);

        wanPisuDownloaderRecycler = findViewById(R.id.wanpisu_downloader_recycler);

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
}