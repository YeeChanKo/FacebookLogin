package com.example.viz.facebooklogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private CallbackManager callbackManager;

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        // Initialize the SDK before executing any other operations,
        // especially, if you're using Facebook UI elements.

        // create callback manager and register it to the login manager
        // define the behaviors when user try to log in with the callback manager
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // 다음 화면으로 넘기기
                Toast.makeText(getApplicationContext(), "로그인 성공!!!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, PublishActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancel() {
                // 취소했다고 알려주기
                Toast.makeText(getApplicationContext(), "로그인을 취소하였습니다", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                // 오류 발생했다고 알려주기
                Toast.makeText(getApplicationContext(), "오류가 발생하였습니다", Toast.LENGTH_SHORT).show();
            }
        });

        setContentView(R.layout.activity_main);

        // find custom login button and register onclick listener to login with facebook
        // setcontentview 뒤에 넣어야 view 찾을 수 있음
        Button facebookLoginButton = (Button) findViewById(R.id.facebook_login_button);
        facebookLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("public_profile", "user_friends"));
            }
        });

        Button facebookPublishButton = (Button) findViewById(R.id.facebook_publish_button);
        facebookPublishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccessToken token = AccessToken.getCurrentAccessToken();
                if(token != null && !token.isExpired())
                    startActivity(new Intent(MainActivity.this, PublishActivity.class));
                else
                    Toast.makeText(getApplicationContext(), "(다시) 로그인하세요", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}