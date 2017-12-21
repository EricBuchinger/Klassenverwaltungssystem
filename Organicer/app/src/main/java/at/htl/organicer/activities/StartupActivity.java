package at.htl.organicer.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import java.io.IOException;
import at.htl.organicer.R;
import at.htl.organicer.authentication.AuthenticationActivity;
import at.htl.organicer.entities.WebUntisUser;
import at.htl.organicer.fragments.StartUpFragmentPortrait;
import at.htl.organicer.fragments.WebUntisUserFragment;
import at.htl.organicer.rest.RestHelperAlternative;
import at.htl.organicer.rest.SessionDataHelper;

public class StartupActivity extends AppCompatActivity implements WebUntisUserFragment.OnFragmentInteractionListener {
    public static SessionDataHelper dataHelper;
    private FragmentManager fragmentManager;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private static int RID=1234;
    private static String TAG="StartupActivity";
    private WebUntisUserFragment webUntisUserFragment;
    ValueEventListener postListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);


        //Init Firebase Database and Authentication
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();


        dataHelper = new SessionDataHelper();
        fragmentManager = getSupportFragmentManager();

        postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                WebUntisUser user = dataSnapshot.getValue(WebUntisUser.class);
                webUntisUserFragment = (WebUntisUserFragment) fragmentManager.findFragmentById(R.id.webuntis_user);
                if(user==null){
                    Log.d(TAG,"Kein Webuntisuser eingetragen");
                    if (webUntisUserFragment == null)
                        webUntisUserFragment = new WebUntisUserFragment();

                    fragmentManager.beginTransaction().replace(R.id.container_main, webUntisUserFragment, null).commit();
                }
                else{
                    try {
                        String sessionId = RestHelperAlternative.authUser(user.getUsername(), user.getPassword());
                        if(sessionId==null){
                            Log.e(TAG,"Falscher Benutzername oder Passwort!");
                            showError("Sie haben einen falschen Benutzernamen oder ein falsches Passwort eingegeben!");

                            if(webUntisUserFragment==null) {
                                webUntisUserFragment = new WebUntisUserFragment();
                            }
                            fragmentManager.beginTransaction().replace(R.id.container_main, webUntisUserFragment, null).commit();
                        }
                        if(sessionId!=null) {
                            dataHelper.setSessionId(sessionId);
                            dataHelper = RestHelperAlternative.getDataFromWebuntis(dataHelper);
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.container_main, new StartUpFragmentPortrait())
                                    .commit();
                        }

                    } catch (JSONException | IOException e) {
                        Log.e(TAG+"/onDataChange","Error in ValueEventListener!");
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG+"/onCancelled","Error in ValueEventListener!");
            }
        };



        //startAuthentication
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signOut();
        Intent intent = new Intent(this, AuthenticationActivity.class);
        if (auth.getCurrentUser() == null) {
            startActivityForResult(intent,RID);
        } else if (auth.getCurrentUser() != null) {
            Toast.makeText(this, "Already authenticated with User "+auth.getCurrentUser().getDisplayName(), Toast.LENGTH_LONG).show();
            onActivityResult(RID,0,null);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RID)
            mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).addValueEventListener(postListener);

    }

    private void showError(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(message)
                .setTitle("Error");

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
