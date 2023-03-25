package in.ghostreborn.wanpisu;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.parser.AllAnime;

public class AnimeAsync extends AsyncTask<String, Void, ArrayList<String>> {

    private ArrayList<String> servers = new ArrayList<>();
    String animeID;
    Context context;

    public AnimeAsync(String mAnimeID, Context mContext){
        animeID = mAnimeID;
        context = mContext;
    }

    @Override
    protected ArrayList<String> doInBackground(String... strings) {
        servers = AllAnime.getAnimeServer(animeID);
        return servers;
    }

    @Override
    protected void onPostExecute(ArrayList<String> strings) {
        super.onPostExecute(strings);

        ArrayList<String> servers = strings;

        AnimeDetailsActivity.initPlayer(servers.get(0), context);

    }
}
