package com.javabootcamp.assessment2.features.filetransfer;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class SftpFileTransferServiceImpl implements FileTransferService {

    private Logger logger = LoggerFactory.getLogger(SftpFileTransferServiceImpl.class);

    @Value("${sftp.host}")
    private String host;

    @Value("${sftp.port}")
    private Integer port;

    @Value("${sftp.username}")
    private String username;

    @Value("${sftp.password}")
    private String password;

    @Value("${sftp.sessionTimeout}")
    private Integer sessionTimeout;

    @Value("${sftp.channelTimeout}")
    private Integer channelTimeout;

    @Override
    public boolean uploadFile(InputStream localInputStream, String remoteFilePath) {
        ChannelSftp channelSftp = null;
        try {
            channelSftp = createChannelSftp();
            if (channelSftp != null) {
                channelSftp.put(localInputStream, remoteFilePath);
                return true;
            }
        } catch(SftpException e) {
            logger.error("Error upload file", e);
        } finally {
            disconnectChannelSftp(channelSftp);
        }
        return false;
    }

    private ChannelSftp createChannelSftp() {
        try {
            JSch jSch = new JSch();
            Session session = jSch.getSession(username, host, port);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(password);
            session.connect(sessionTimeout);
            Channel channel = session.openChannel("sftp");
            channel.connect(channelTimeout);
            return (ChannelSftp) channel;
        } catch(JSchException ex) {
            logger.error("Create ChannelSftp error", ex);
        }
        return null;
    }

    private void disconnectChannelSftp(ChannelSftp channelSftp) {
        try {
            if( channelSftp == null)
                return;

            if(channelSftp.isConnected())
                channelSftp.disconnect();

            if(channelSftp.getSession() != null)
                channelSftp.getSession().disconnect();

        } catch(JSchException ex) {
            logger.error("SFTP disconnect error", ex);
        }
    }
}
