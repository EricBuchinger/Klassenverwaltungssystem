package at.htl.organicer.database;

import android.content.Context;
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
import at.htl.organicer.authentication.AuthenticationActivity;
import at.htl.organicer.entities.Event;
import at.htl.organicer.entities.WebUntisUser;
import at.htl.organicer.fragments.LoadingbarFragment;
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
    private LoadingbarFragment loadingbarFragment;
    private Context context;

    public int getKlassenId() {
        return klassenId;
    }

    public void setKlassenId(int klassenId) {
        this.klassenId = klassenId;
    }

    private int klassenId;

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
        loadingbarFragment = new LoadingbarFragment();


        postUserListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                webUntisUser = dataSnapshot.getValue(WebUntisUser.class);
                WebUntisUserFragment webUntisUserFragment = (WebUntisUserFragment) fragmentManager.findFragmentById(R.id.webuntis_user);
                if(webUntisUser==null){
                    Log.d(TAG,"Kein Webuntisuser eingetragen");
                    if (webUntisUserFragment == null)
                        webUntisUserFragment = new WebUntisUserFragment();

                    fragmentManager.beginTransaction().replace(R.id.container_main, webUntisUserFragment, "WebUntisUserFragment").commit();
                }
                else{
                    try {
                        RestHelperAlternative.AuthData authData = RestHelperAlternative.authUser(webUntisUser.getUsername(), webUntisUser.getPassword());
                        if(authData.getSessionId()==null){
                            Log.e(TAG,"Falscher Benutzername oder Passwort!");
                                showError("Sie haben einen falschen Benutzernamen oder ein falsches Passwort eingegeben!");

                            if(webUntisUserFragment==null) {
                                webUntisUserFragment = new WebUntisUserFragment();
                            }
                            fragmentManager.beginTransaction().replace(R.id.container_main, webUntisUserFragment, "WebUntisUserFragment").commit();
                            //fragmentManager.beginTransaction().add(R.id.container_main, loadingbarFragment, "LoadingbarFragment").commit();
                        }
                        if(authData.getSessionId()!=null) {
                            dataHelper.setAuthData(authData);
                            dataHelper = RestHelperAlternative.getDataFromWebuntis(dataHelper);
                            //fragmentManager.beginTransaction().remove(loadingbarFragment).commit(); //todo
                            //FIXME //TODO //FIXME //TODO //FIXME //TODO //FIXME //TODO
                            setKlassenId(Integer.parseInt(dataHelper.getAuthData().getKlasseId()));
                            fragmentManager
                                    .beginTransaction()
                                    .replace(R.id.container_main, new StartUpFragmentPortrait(),"StartUpPortraitFragment")
                                    .commit();
                            //context.getApplicationContext()
                            //AuthenticationActivity.getInstance().onBackPressed(); FIXME
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
                StartUpFragmentPortrait startUpFragmentPortrait = (StartUpFragmentPortrait)fragmentManager
                        .findFragmentByTag("StartUpPortraitFragment");
                if(startUpFragmentPortrait!=null)
                    startUpFragmentPortrait.notifyAdapter();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        addEventListener();
    }
    private void showError(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(StartupActivity.getInstance());

        builder.setMessage(message)
                .setTitle("Error");

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
