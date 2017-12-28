package za.co.dladle.thirdparty.document;

import java.io.IOException;

/**
 * Created by prady on 6/10/2017.
 */
public interface DocumentManagementService {
    String uploadPhoto(String folderName,String path, String image, String fileName) throws IOException;

    public String uploadAudio(String folderName,String path, String audio, String fileName) throws IOException;
}
