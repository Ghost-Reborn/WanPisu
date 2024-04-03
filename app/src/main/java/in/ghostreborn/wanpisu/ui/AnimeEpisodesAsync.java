package in.ghostreborn.wanpisu.ui;

import android.content.Context;
import android.os.AsyncTask;

import androidx.recyclerview.widget.LinearLayoutManager;

import in.ghostreborn.wanpisu.adapter.AnimeEpisodesAdapter;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.parser.JikanParser;

public class AnimeEpisodesAsync extends AsyncTask<Void, Void, Void> {

    String page;
    Context context;

    public AnimeEpisodesAsync(String page, Context context) {
        this.page = page;
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String malID = WanPisuConstants.wanPisus.get(WanPisuConstants.ANIME_INDEX).getMalID();
        JikanParser.parseAnimeEpisodes(malID, page);
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);

        String animeID = WanPisuConstants.wanPisus.get(WanPisuConstants.ANIME_INDEX)
                .getAnimeID();
        // TODO fix this
        int episodes = 1;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        AnimeEpisodesActivity.animeContainerView.setLayoutManager(linearLayoutManager);
        AnimeEpisodesAdapter adapter = new AnimeEpisodesAdapter(episodes, context, animeID);
        AnimeEpisodesActivity.animeContainerView.setAdapter(adapter);


    }
}
