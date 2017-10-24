package za.co.dladle.thirdparty.document;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;

/**
 * Created by prady on 10/2/2017.
 */
@Component
public class DocumentManagementServiceSambaImpl implements DocumentManagementService {
    @Override
    public String upload(String image, String fileName) throws IOException {
        String user = "dladlesamba:samba@27730";
        NtlmPasswordAuthentication ntlmPasswordAuthentication = new NtlmPasswordAuthentication(user);
        String path = "smb://asy40-nix01.wadns.net/Restricted/demo/" + fileName;
        SmbFile smbFile = new SmbFile(path, ntlmPasswordAuthentication);
        byte[] data = Base64.getDecoder().decode(image);
        SmbFileOutputStream smbFileOutputStream = new SmbFileOutputStream(smbFile);
        smbFileOutputStream.write(data);
        System.out.println("Done");
        return path;
    }

    @Override
    public String uploadAudio(String audio, String fileName) throws IOException {
        String user = "dladlesamba:samba@27730";
        NtlmPasswordAuthentication ntlmPasswordAuthentication = new NtlmPasswordAuthentication(user);
        String path = "smb://asy40-nix01.wadns.net//Restricted//demo" + fileName;
        SmbFile smbFile = new SmbFile(path, ntlmPasswordAuthentication);
        byte[] data = Base64.getDecoder().decode("data:audio/mpeg;base64," + audio);
        SmbFileOutputStream smbFileOutputStream = new SmbFileOutputStream(smbFile);
        smbFileOutputStream.write(data);
        return path;
    }
}
