package com.codeframetech.jobchaiyoo;
/**
 * Created by Digition on 12/24/2017.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.codeframetech.jobchaiyoo.controller.NetworkCheckClass;
import com.codeframetech.jobchaiyoo.database.DatabaseOperation;
import com.codeframetech.jobchaiyoo.helpers.RegisterFacebookUsers;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FacebookActivity extends AppCompatActivity {

    Context c;
    @BindView(R.id.login_button)
    LoginButton mLoginButton;
    @BindView(R.id.title)
    TextView title_tv;
    @BindView(R.id.sub_title)
    TextView sub_title_tv;
    private CallbackManager mCallbackManager;
    public static final String PROFILE_NAME = "PROFILE_FIRST_NAME";
    public static final String PROFILE_EMAIL = "PROFILE_EMAIL";
    public static final String PROFILE_IMAGE_URL = "PROFILE_IMAGE_URL";
    String email, fullName, profile_picture;
    String type = "login";
    DatabaseOperation mDatabase;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_facebook);
        ButterKnife.bind(this);
        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Loggin in...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);


        mCallbackManager = CallbackManager.Factory.create();
        mLoginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        mLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // AccessToken accessToken = loginResult.getAccessToken();
                // Profile profile = Profile.getCurrentProfile();
                mProgress.show();
                mLoginButton.setVisibility(View.GONE);
                nextActivity();

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(FacebookActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

            }
        });
        if (AccessToken.getCurrentAccessToken() != null) {
            if (new NetworkCheckClass().checkConnection(this)) {
                // Profile profile = Profile.getCurrentProfile();
                mProgress.dismiss();
                nextActivity();
                mLoginButton.setVisibility(View.INVISIBLE);
                // fb_progressbar.setVisibility(View.VISIBLE);
            } else {
                Intent intent = new Intent(FacebookActivity.this, NavigationActivity.class);
                startActivity(intent);
                finish();
            }
        }


        mDatabase = new DatabaseOperation(this);
        //Typeface declaration
        final Typeface typeface = Typeface.createFromAsset(this.getAssets(), "fonts/ManilaSansBld.otf");
        title_tv.setTypeface(typeface);
        final Typeface tf = Typeface.createFromAsset(this.getAssets(), "fonts/sans.ttf");
        sub_title_tv.setTypeface(tf);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data
        );
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(getApplication());
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    private void nextActivity() {
        final Intent main = new Intent(FacebookActivity.this, NavigationActivity.class);
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                JSONObject json = response.getJSONObject();

                try {
                    if (json != null) {

                        if (json.has("email") && json.has("name")) {
                            email = json.getString("email");
                            fullName = json.getString("name");
                            profile_picture = json.getString("id");
                            RegisterFacebookUsers registerFacebookUsers = new RegisterFacebookUsers(c, mDatabase);
                            registerFacebookUsers.execute(type, fullName, email, profile_picture);

                        } else {

                            email = "welcome!!";
                            fullName = json.getString("name");
                            profile_picture = json.getString("id");
                            RegisterFacebookUsers registerFacebookUsers = new RegisterFacebookUsers(c, mDatabase);
                            registerFacebookUsers.execute(type, fullName, email, profile_picture);


                        }


                    }

                    //String profile_picture = json.getString("id");
                    main.putExtra(PROFILE_IMAGE_URL, profile_picture);
                    main.putExtra(PROFILE_NAME, fullName);
                    main.putExtra(PROFILE_EMAIL, email);
                    startActivity(main);

                    finish();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email,name,picture");
        request.setParameters(parameters);
        request.executeAsync();


    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


}
