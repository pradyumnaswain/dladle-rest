package za.co.dladle.thirdparty.document;

import java.io.IOException;

/**
 * Created by prady on 6/10/2017.
 */
public interface DocumentManagementService {
    String upload(String image, String fileName) throws IOException;
    public String uploadAudio(String audio, String fileName) throws IOException;
}
