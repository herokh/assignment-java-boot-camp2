package com.javabootcamp.assessment2.features.filetransfer;

import java.io.InputStream;

public interface FileTransferService {
    boolean uploadFile(InputStream localFilePath, String remoteFilePath);
}
