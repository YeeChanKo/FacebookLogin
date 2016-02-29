package com.example.viz.facebooklogin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;

import java.util.Arrays;

public class PublishActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);

        // 쓰기 권한 재요청
        LoginManager.getInstance().logInWithPublishPermissions(this, Arrays.asList("publish_actions"));

        Button publishButton = (Button) findViewById(R.id.publishButton);
        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String content = ((EditText)findViewById(R.id.publishTextField)).getText().toString();
                GraphRequest request = GraphRequest.newPostRequest(AccessToken.getCurrentAccessToken(), "me/feed",)
                request.executeAsync();
            }
        });
    }
}
