package za.co.dladle.thirdparty.document;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;

import java.io.IOException;
import java.util.Base64;

/**
 * Created by prady on 10/2/2017.
 */
public class DocumentManagementServiceSambaImpl implements DocumentManagementService {
    @Override
    public String upload(String image, String fileName) throws IOException {
        String user = "domain;userName:password";
        NtlmPasswordAuthentication ntlmPasswordAuthentication = new NtlmPasswordAuthentication(user);
        String path = "smb://url/" + fileName;
        SmbFile smbFile = new SmbFile(path, ntlmPasswordAuthentication);
        byte[] data = Base64.getDecoder().decode("data:image/png;base64," + image);
        SmbFileOutputStream smbFileOutputStream = new SmbFileOutputStream(smbFile);
        smbFileOutputStream.write(data);
        return path;
    }

    @Override
    public String uploadAudio(String audio, String fileName) throws IOException {
        String user = "domain;userName:password";
        NtlmPasswordAuthentication ntlmPasswordAuthentication = new NtlmPasswordAuthentication(user);
        String path = "smb://url/" + fileName;
        SmbFile smbFile = new SmbFile(path, ntlmPasswordAuthentication);
        byte[] data = Base64.getDecoder().decode("data:audio/mpeg;base64," + audio);
        SmbFileOutputStream smbFileOutputStream = new SmbFileOutputStream(smbFile);
        smbFileOutputStream.write(data);
        return path;
    }
}
