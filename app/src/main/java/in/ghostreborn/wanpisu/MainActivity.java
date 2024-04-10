package in.ghostreborn.wanpisu;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // TODO Get recently updated anime's AllAnime ID and parse latest episode and show it in main screen
    // TODO show multiple servers
    // TODO fix playback of modern ts files
    // TODO show selection of manga chapters

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}