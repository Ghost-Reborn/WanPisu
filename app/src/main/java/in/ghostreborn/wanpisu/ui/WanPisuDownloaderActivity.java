package in.ghostreborn.wanpisu.ui;

import android.os.Bundle;

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
        AnimeDownloadAdapter adapter = new AnimeDownloadAdapter(WanPisuConstants.animeDowns);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        wanPisuDownloaderRecycler.setLayoutManager(manager);
        wanPisuDownloaderRecycler.setAdapter(adapter);

    }
}