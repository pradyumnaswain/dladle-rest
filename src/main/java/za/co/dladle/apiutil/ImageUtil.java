package za.co.dladle.apiutil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ImageUtil {

    @Value("${document.store.url}")
    private String documentUrl;

    public String getFullImagePath(Long userId, String profilePicture) {

        return profilePicture != null ? documentUrl + "profile/" + userId + "/" + profilePicture : null;
    }
}
