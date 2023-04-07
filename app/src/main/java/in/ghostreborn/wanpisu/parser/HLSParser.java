package in.ghostreborn.wanpisu.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HLSParser {

    public static ArrayList<String> getMainServers(String url) {

        ArrayList<String> servers = new ArrayList<>();

        try {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            BufferedReader reader = new BufferedReader(new StringReader(response.body().string()));
            String line;
            while ((line = reader.readLine()) != null) {
                servers.add(line);
            }

            servers.remove(0);
            int i = 0;
            while (i < servers.size()) {
                servers.remove(i);
                i++;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return servers;

    }

    public static ArrayList<String> getSubServers(String url) {

        ArrayList<String> servers = new ArrayList<>();

        try {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            BufferedReader reader = new BufferedReader(new StringReader(response.body().string()));
            String line;
            while ((line = reader.readLine()) != null) {
                servers.add(line);
            }

            servers.remove(0);
            servers.remove(0);
            servers.remove(0);
            servers.remove(0);
            int i = 0;
            while (i < servers.size()) {
                servers.remove(i);
                i++;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return servers;

    }

}
