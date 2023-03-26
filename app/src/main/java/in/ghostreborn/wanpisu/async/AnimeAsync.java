package in.ghostreborn.wanpisu.async;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.parser.AllAnime;
import in.ghostreborn.wanpisu.ui.ExoPlayerActivity;

public class AnimeAsync extends AsyncTask<String, Void, ArrayList<String>> {

    String animeID;
    Context context;
    String episodeNumber;

    public AnimeAsync(String mAnimeID, Context mContext, String mEpisodeNumber) {
        animeID = mAnimeID;
        context = mContext;
        episodeNumber = mEpisodeNumber;
    }

    @Override
    protected ArrayList<String> doInBackground(String... strings) {
        return AllAnime.getAnimeServer(animeID, episodeNumber);
    }

    @Override
    protected void onPostExecute(ArrayList<String> strings) {
        super.onPostExecute(strings);

        ArrayList<String> servers = strings;
        ExoPlayerActivity.initPlayer(servers.get(0), context);

    }
}
