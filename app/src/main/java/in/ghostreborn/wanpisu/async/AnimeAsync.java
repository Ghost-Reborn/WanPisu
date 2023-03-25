package in.ghostreborn.wanpisu.async;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.ui.AnimeDetailsActivity;
import in.ghostreborn.wanpisu.parser.AllAnime;

public class AnimeAsync extends AsyncTask<String, Void, ArrayList<String>> {

    String animeID;
    Context context;

    public AnimeAsync(String mAnimeID, Context mContext){
        animeID = mAnimeID;
        context = mContext;
    }

    @Override
    protected ArrayList<String> doInBackground(String... strings) {
        return AllAnime.getAnimeServer(animeID);
    }

    @Override
    protected void onPostExecute(ArrayList<String> strings) {
        super.onPostExecute(strings);

        AnimeDetailsActivity.initPlayer(strings.get(0), context);

    }
}
