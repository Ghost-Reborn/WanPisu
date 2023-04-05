package in.ghostreborn.wanpisu.manager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class WanPisuDownloadManager {

    private final OkHttpClient client = new OkHttpClient();

    public interface ProgressListener {
        void onProgress(long bytesRead, long contentLength, boolean done);
    }

    public void download(String url, String destPath, ProgressListener listener) throws IOException {
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

}
