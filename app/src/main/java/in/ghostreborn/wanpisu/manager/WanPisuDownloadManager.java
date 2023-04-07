package in.ghostreborn.wanpisu.manager;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import in.ghostreborn.wanpisu.async.AnimeHLSMainAsync;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class WanPisuDownloadManager {

    public static String animeSubServer = null;
    Dispatcher dispatcher;
    ConnectionPool connectionPool;

    public void download(String url, String destPath, ProgressListener listener) throws IOException {

        dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(5);
        dispatcher.setMaxRequestsPerHost(3);

        connectionPool = new ConnectionPool(5, 30, TimeUnit.SECONDS);

        if (isHLSDownload(url)) {
            return;
        }

        OkHttpClient client = new OkHttpClient.Builder()
                .dispatcher(dispatcher)
                .connectionPool(connectionPool)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Failed to download file: " + response);
        }

        ResponseBody body = response.body();
        long contentLength = body.contentLength();

        InputStream inputStream = body.byteStream();

        File destFile = new File(destPath);
        FileOutputStream outputStream = new FileOutputStream(destFile);

        byte[] buffer = new byte[4096];
        int len;
        long downloaded = 0;

        while ((len = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
            downloaded += len;
            listener.onProgress(downloaded, contentLength, false);
        }

        outputStream.flush();

        listener.onProgress(downloaded, contentLength, true);
    }

    private boolean isHLSDownload(String url) {
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .dispatcher(dispatcher)
                    .connectionPool(connectionPool)
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            if (response.body().string().contains("#EXT")) {
                new AnimeHLSMainAsync(url).execute();
                new Thread(() -> {
                    boolean shouldContinue =  true;
                    while (shouldContinue) {
                        if (animeSubServer!=null) {
                            int i=0;
                            while (i< WanPisuConstants.animeServes.size()){
                                WanPisuDownloadManager manager = new WanPisuDownloadManager();
                                try {
                                    manager.download(WanPisuConstants.animeServes.get(i), "/sdcard/file-" + i + ".mp4", new ProgressListener() {
                                        @Override
                                        public void onProgress(long bytesRead, long contentLength, boolean done) {
                                            Log.e("DOWNLOADING HLS", "Downloaded: " + bytesRead + "\tTotal: " + contentLength);
                                        }
                                    });
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                i++;
                            }
                            shouldContinue = false;
                        }else {
                            Log.e("ANIME_TEST", "RETRYING");
                        }
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }).start();
                return true;
            }
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public interface ProgressListener {
        void onProgress(long bytesRead, long contentLength, boolean done);
    }

}
