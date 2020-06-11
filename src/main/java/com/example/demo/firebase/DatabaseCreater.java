package com.example.demo.firebase;

import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.time.LocalDateTime;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

@Service
public class DatabaseCreater {

   public  FirebaseApp app ;

    @PostConstruct
    public  void initalize () throws IOException, ExecutionException, InterruptedException {

         InputStream inputStream = getClass().getResourceAsStream("/serviceAccountKey.json");

        //FileInputStream serviceAccount = new FileInputStream(inputStream);
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(inputStream);

        AccessToken token =  googleCredentials.getAccessToken();

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(googleCredentials)
                .setDatabaseUrl("https://classroom-4d8db.firebaseio.com")
                .build();
        FirebaseApp  app = FirebaseApp.initializeApp(options);


    }
}
