package za.co.dladle.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

/**
 * Created by prady on 6/4/2017.
 */
@Service
public class AndroidPushNotificationsService {
    private static final String FIREBASE_SERVER_KEY = "AAAAqV0vOfg:APA91bFJchj_8YmAdmxJdg7POK-3Bm8QU1ILKQI4Ylpkm6mMxU2pnyhNgXoJrLZR8DIzAgeDn5EYoL1ZJRpO4eRd2BO5PfN-BF_CtXh2QbW3SqJa5kDB21fj0MPdqBSrA1IXbAFc6A22";

    @Async
    public CompletableFuture<FirebaseResponse> send(HttpEntity<String> entity) {

        RestTemplate restTemplate = new RestTemplate();

        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
        interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
        restTemplate.setInterceptors(interceptors);

        FirebaseResponse firebaseResponse = restTemplate.postForObject("https://fcm.googleapis.com/fcm/send", entity, FirebaseResponse.class);

        return CompletableFuture.completedFuture(firebaseResponse);
    }

}
