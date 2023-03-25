package in.ghostreborn.wanpisu.network;

import android.os.AsyncTask;

import in.ghostreborn.wanpisu.MainActivity;
import in.ghostreborn.wanpisu.parser.AllAnime;

public class AnimeSearchAsync extends AsyncTask<String, Void, String> {

    public AnimeSearchAsync(){}

    /**
     * @param strings - is the anime name
     * @return animeID used by AllAnime
     */
    @Override
    protected String doInBackground(String... strings) {
        return AllAnime.parseSearchQuery(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        MainActivity.testText.setText(s);

    }
}
