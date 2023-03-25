package in.ghostreborn.wanpisu.parser;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import in.ghostreborn.wanpisu.MainActivity;

public class AllAnime {

    public static String parseSearchQuery(String animeName){
        String apiUrl = "https://api.allanime.to/allanimeapi?variables={\"search\":{\"allowAdult\":false,\"allowUnknown\":false,\"query\":\"" +
                animeName +
                "\"},\"limit\":40,\"page\":1,\"translationType\":\"sub\",\"countryOrigin\":\"ALL\"}&query=query($search:SearchInput,$limit:Int,$page:Int,$translationType:VaildTranslationTypeEnumType,$countryOrigin:VaildCountryOriginEnumType){shows(search:$search,limit:$limit,page:$page,translationType:$translationType,countryOrigin:$countryOrigin){edges{_id,name}}}";
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            urlConnection.disconnect();
        } catch (MalformedURLException e) {
            Log.e(MainActivity.LOG_TAG, "Unable to parse URL");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

}
