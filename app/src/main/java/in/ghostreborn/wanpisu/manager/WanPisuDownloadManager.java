package in.ghostreborn.wanpisu.manager;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class WanPisuDownloadManager {

    Dispatcher dispatcher;
    ConnectionPool connectionPool;

    public void download(String url, String destPath, ProgressListener listener) throws IOException {

        dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(5);
        dispatcher.setMaxRequestsPerHost(3);

        connectionPool = new ConnectionPool(5, 30, TimeUnit.SECONDS);

        if (isHLSDownload(url)){
            Log.e("TAG", "HLS: " + url);
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

        if (downloaded != contentLength) {
            throw new IOException("Failed to download file: downloaded " + downloaded + " bytes, expected " + contentLength);
        }

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
