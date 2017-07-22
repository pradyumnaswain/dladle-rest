package za.co.dladle.thirdparty;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import za.co.dladle.session.UserSession;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by prady on 6/10/2017.
 */
@Service
public class FileManagementServiceImpl implements FileManagementService {
    @Value("${document.store.address}")
    private String path;

    @Autowired
    private ApplicationContext applicationContext;

    public String upload(String image, String fileName) throws IOException {
        UserSession userSession = applicationContext.getBean(UserSession.class);
        byte[] decodedImg = Base64.getDecoder().decode(image.getBytes(StandardCharsets.UTF_8));
        Path destinationFile = Paths.get(path, userSession.getUser().getUserId().toString(), fileName);
        Files.write(destinationFile, decodedImg);

        return fileName;
    }
}
