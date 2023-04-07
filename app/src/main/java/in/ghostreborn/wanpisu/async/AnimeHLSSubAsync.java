package in.ghostreborn.wanpisu.async;

import android.os.AsyncTask;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.manager.WanPisuDownloadManager;
import in.ghostreborn.wanpisu.parser.HLSParser;

public class AnimeHLSSubAsync extends AsyncTask<Void, Void, ArrayList<String>> {

    String url;
    public AnimeHLSSubAsync(String url){
        this.url = url;
    }

    @Override
    protected ArrayList<String> doInBackground(Void... voids) {
        return HLSParser.getSubServers(url);
    }

    @Override
    protected void onPostExecute(ArrayList<String> servers) {
        super.onPostExecute(servers);
        WanPisuConstants.animeServes = servers;
        WanPisuDownloadManager.animeSubServer = servers.get(0);
    }
}
