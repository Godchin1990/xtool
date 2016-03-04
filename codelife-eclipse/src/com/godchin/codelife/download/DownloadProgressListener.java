package com.godchin.codelife.download;

public interface DownloadProgressListener {
    void onDownloadSize(int size);

    void onDownfinisih(int fileSize);
}
