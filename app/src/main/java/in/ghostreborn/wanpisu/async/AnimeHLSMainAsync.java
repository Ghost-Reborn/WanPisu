package in.ghostreborn.wanpisu.async;

import android.os.AsyncTask;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.parser.HLSParser;

public class AnimeHLSMainAsync extends AsyncTask<Void, Void, ArrayList<String>> {

    String url;

    public AnimeHLSMainAsync(String url) {
        this.url = url;
    }

    @Override
    protected ArrayList<String> doInBackground(Void... voids) {
        return HLSParser.getMainServers(url);
    }

    @Override
    protected void onPostExecute(ArrayList<String> servers) {
        super.onPostExecute(servers);
        new AnimeHLSSubAsync(servers.get(0)).execute();
    }
}

