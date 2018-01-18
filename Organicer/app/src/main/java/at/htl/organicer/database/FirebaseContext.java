package at.htl.organicer.database;

import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import at.htl.organicer.R;
import at.htl.organicer.activities.StartupActivity;
import at.htl.organicer.entities.Event;
import at.htl.organicer.entities.WebUntisUser;
import at.htl.organicer.fragments.StartUpFragmentPortrait;
import at.htl.organicer.fragments.WebUntisUserFragment;
import at.htl.organicer.rest.RestHelperAlternative;
import at.htl.organicer.rest.SessionDataHelper;

import static at.htl.organicer.activities.StartupActivity.dataHelper;
import static at.htl.organicer.activities.StartupActivity.fragmentManager;

/**
 * Created by eric on 12.01.18.
 */

public class FirebaseContext {

    public DatabaseReference mDatabase;
    public FirebaseAuth mAuth;
    private static FirebaseContext instance;
    private final String TAG = "FirebaseContext";
    private ValueEventListener postUserListener;
    private ValueEventListener postEventListener;
    public LinkedList<Event> events;
    public WebUntisUser webUntisUser;

    public static FirebaseContext getInstance(){
        if(instance==null){
            return new FirebaseContext();
        }
        else
            return instance;
    }

    public void addUserListener(){
        mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).addValueEventListener(postUserListener);
    }

    public void addEventListener(){
        mDatabase.child("events").addValueEventListener(postEventListener);
    }

    private FirebaseContext(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        events = new LinkedList<>();
        webUntisUser = new WebUntisUser();

        postUserListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                webUntisUser = dataSnapshot.getValue(WebUntisUser.class);
                WebUntisUserFragment webUntisUserFragment = (WebUntisUserFragment) fragmentManager.findFragmentById(R.id.webuntis_user);
                if(webUntisUser==null){
                    Log.d(TAG,"Kein Webuntisuser eingetragen");
                    if (webUntisUserFragment == null)
                        webUntisUserFragment = new WebUntisUserFragment();

                    fragmentManager.beginTransaction().replace(R.id.container_main, webUntisUserFragment, null).commit();
                }
                else{
                    try {
                        String sessionId = RestHelperAlternative.authUser(webUntisUser.getUsername(), webUntisUser.getPassword());
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
                            fragmentManager
                                    .beginTransaction()
                                    .replace(R.id.container_main, new StartUpFragmentPortrait())
                                    .commit();
                        }

                    } catch (JSONException | IOException e) {
                        Log.e(TAG,"Error in ValueEventListener!");
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG,"Error in ValueEventListener!");
            }
        };

        postEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                events.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Event e = postSnapshot.getValue(Event.class);
                    events.add(e);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        addEventListener();
    }
    private void showError(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(StartupActivity.context);

        builder.setMessage(message)
                .setTitle("Error");

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
