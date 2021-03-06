package za.co.dladle.thirdparty.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import za.co.dladle.session.UserSession;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * Created by prady on 6/10/2017.
 */
@Service
public class DocumentManagementServiceImpl implements DocumentManagementService {
    @Value("${document.store.address}")
    private String path;

    @Override
    public String uploadPhoto(String folderName, String folderPath, String image, String fileName) throws IOException {

        byte[] decodedImg = javax.xml.bind.DatatypeConverter.parseBase64Binary(image);

        Path destinationFolder = Paths.get(path + "/" + folderPath, folderName);
        createFolderIfNotExist(destinationFolder);

        File destinationFile = new File(Paths.get(destinationFolder.toString(), fileName).toString());
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFile);
        fileOutputStream.write(decodedImg);

        System.out.println(destinationFile.toString());
        fileOutputStream.close();

        return fileName;
    }

    @Override
    public String uploadAudio(String folderName, String folderPath, String audio, String fileName) throws IOException {
        byte[] decodedImg = javax.xml.bind.DatatypeConverter.parseBase64Binary(audio);

        Path destinationFolder = Paths.get(path + folderPath, folderName);
        createFolderIfNotExist(destinationFolder);

        File destinationFile = new File(Paths.get(destinationFolder.toString(), fileName).toString());
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFile);
        fileOutputStream.write(decodedImg);

        System.out.println(destinationFile.toString());
        fileOutputStream.close();

        return fileName;

    }

    private void createFolderIfNotExist(Path folder) {
        if (!Files.exists(folder)) {
            try {
                Files.createDirectory(folder);
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }
}
