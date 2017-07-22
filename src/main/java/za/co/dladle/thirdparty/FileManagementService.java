package za.co.dladle.thirdparty;

import java.io.IOException;

/**
 * Created by prady on 6/10/2017.
 */
public interface FileManagementService {
    String upload(String image, String fileName) throws IOException;
}
