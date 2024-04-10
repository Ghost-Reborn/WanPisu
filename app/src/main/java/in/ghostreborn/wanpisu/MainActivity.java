package in.ghostreborn.wanpisu;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // TODO Created a way to get details of anime through allAnime ID, USE IT!
    // TODO instead of using anilist, store AllAnime ID and show it in user's section
    // TODO fix One Piece 1100 episode playing differently
    // TODO show a button to refresh Anilist from server
    // TODO Store AllAnime ID when anilist fragment doesn't have one
    // TODO Show episode titles using jikan
    // TODO Show a way to add anime to anilist with malID
    // TODO show multiple servers
    // TODO fix playback of modern ts files
    // TODO show selection of manga chapters

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}