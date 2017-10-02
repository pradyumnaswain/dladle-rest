package za.co.dladle.thirdparty.document;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by prady on 6/10/2017.
 */
@Service
public class DocumentManagementServiceCloudinaryImpl implements DocumentManagementService {
    @Value("${cloudinary.cloud.name}")
    private String cloudName;
    @Value("${cloudinary.api.key}")
    private String apiKey;
    @Value("${cloudinary.api.secret}")
    private String apiSecret;

    public String upload(String image, String fileName) throws IOException {
        Map<String, Object> config = new HashMap<>();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);

        Cloudinary cloudinary = new Cloudinary(config);

        Map resultMap = cloudinary.uploader().upload("data:image/png;base64," + image, ObjectUtils.emptyMap());

        return resultMap.get("secure_url").toString();

    }

    public String uploadAudio(String audio, String fileName) throws IOException {
        Map<String, Object> config = new HashMap<>();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);

        Cloudinary cloudinary = new Cloudinary(config);

        Map resultMap = cloudinary.uploader().upload("data:audio/mpeg;base64," + audio, ObjectUtils.asMap("resource_type", "video"));

        return resultMap.get("secure_url").toString();
    }
}
