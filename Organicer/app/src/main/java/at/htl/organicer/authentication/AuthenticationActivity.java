/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package at.htl.organicer.authentication;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.transition.Transition;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.io.IOException;
import java.io.InputStream;

import at.htl.organicer.R;
import at.htl.organicer.database.FirebaseContext;
import at.htl.organicer.fragments.LoadingbarFragment;


public class AuthenticationActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AuthenticationActivity";
    private FirebaseContext firebaseContext;
    private CallbackManager mCallbackManager;
    private static LoginButton loginButton;
    private AuthCredential credential;
    private static final int RC_SIGN_IN = 9001;
    private static GoogleSignInClient mGoogleSignInClient;
    private ImageView backgroundImageView;
    LoadingbarFragment loadingbarFragment;

    private static AuthenticationActivity instance;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;
        setContentView(R.layout.activity_authentication);

        backgroundImageView = findViewById(R.id.iv_background);
        try {
            backgroundImageView.setImageBitmap(getBitmapFromAssets("background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //backgroundImageView

        firebaseContext = FirebaseContext.getInstance();
        //transaction = getSupportFragmentManager().beginTransaction();

        //Init Facebook Authentication
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);


        //Init Firebase Authentication
        firebaseContext.mAuth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();
        loadingbarFragment = new LoadingbarFragment();

        //Init Google Authentication
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        //Facebook Authentication via LoginButton
        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                Toast.makeText(AuthenticationActivity.this, "Authentication with Facebook canceled.",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                Toast.makeText(AuthenticationActivity.this, "Authentication with Facebook failed.",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    public Bitmap getBitmapFromAssets(String fileName) throws IOException {
        AssetManager assetManager = getAssets();

        InputStream istr = assetManager.open(fileName);
        Bitmap bitmap = BitmapFactory.decodeStream(istr);

        return bitmap;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //If Request Code is for Google signIn, complete the sign in via Firebase
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
                Toast.makeText(AuthenticationActivity.this, "Authentication with Google failed.",
                        Toast.LENGTH_SHORT).show();
            }


            super.onBackPressed(); //FIXME
        }
        //If the requestCode isn't for Google, complete the facebook signIn progress
        else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }

    }

    //Sign in to firebase with credential
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseContext.mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            Toast.makeText(AuthenticationActivity.this, "Authentication with Google successful.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(AuthenticationActivity.this, "Authentication with Google failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        finish();
                    }
                });
    }

    //Sign in in Firebase with the Facebook Token
    private void handleFacebookAccessToken(AccessToken token) {


        Log.d(TAG, "handleFacebookAccessToken:" + token);

        credential = FacebookAuthProvider.getCredential(token.getToken());
       // getSupportFragmentManager().beginTransaction().add(R.id.container_signIn, loadingbarFragment, "LOADING").commit();
        firebaseContext.mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //getSupportFragmentManager().beginTransaction().remove(loadingbarFragment).commit();
                            Toast.makeText(AuthenticationActivity.this, "Authentication with Facebook successful.",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(AuthenticationActivity.this, "Authentication with Facebook failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        //AuthenticationActivity.super.onBackPressed();
                        finish();
                    }
                });
    }

    //React on clicks to the google Sign in button
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
        }
    }

    //Start the sign in
    //
    // Activity of Google
    private void signIn() {
        getSupportFragmentManager().beginTransaction().add(R.id.container_signIn, loadingbarFragment, "LOADING").addToBackStack("loading").commit();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

        //todo
    }

    public static void signOut() {
        FirebaseAuth.getInstance().signOut();
        mGoogleSignInClient.signOut();
        LoginManager.getInstance().logOut();
    }

    public static AuthenticationActivity getInstance() {
        return instance;
    }
}
