package at.htl.organicer.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import at.htl.organicer.R;
import at.htl.organicer.authentication.AuthenticationActivity;
import at.htl.organicer.database.FirebaseContext;
import at.htl.organicer.fragments.WebUntisUserFragment;
import at.htl.organicer.rest.SessionDataHelper;

public class StartupActivity extends AppCompatActivity implements WebUntisUserFragment.OnFragmentInteractionListener {
    public static SessionDataHelper dataHelper;
    public static FragmentManager fragmentManager;
    private static int RID=1234;
    private static String TAG="StartupActivity";
    private FirebaseContext databaseContext;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        databaseContext = FirebaseContext.getInstance();
        context = getApplicationContext();
        dataHelper = new SessionDataHelper();
        fragmentManager = getSupportFragmentManager();

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

        if(requestCode == RID){
            databaseContext.addUserListener();
        }


    }




    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
