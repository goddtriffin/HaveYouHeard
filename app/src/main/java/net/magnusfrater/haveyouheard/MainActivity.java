package net.magnusfrater.haveyouheard;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // views
    private TextView tvQuestion;
    private TextView tvHeard;
    private TextView tvLies;
    private TextView tvNotHeard;
    private Button bYes;
    private Button bNo;

    // Firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final DocumentReference dr = db.collection("HaveYouHeard").document("4vvi2PovaZMRd2ZYJhz6");

    // temp
    private int heardCount = 0;
    private int notHeardCount = 0;
    private int liedCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // views
        initViews();

        // listeners
        attachListeners();

        // initialize Firebase
        initFirebase();
    }

    // set up the views
    private void initViews () {
        tvQuestion  = (TextView)    findViewById(R.id.tvQuestion);
        tvHeard     = (TextView)    findViewById(R.id.tvHeard);
        tvLies      = (TextView)    findViewById(R.id.tvLies);
        tvNotHeard  = (TextView)    findViewById(R.id.tvNotHeard);
        bYes        = (Button)      findViewById(R.id.bYes);
        bNo         = (Button)      findViewById(R.id.bNo);
    }

    // creates functionality for the 'Yes' and 'No' buttons
    private void attachListeners () {
        // yes
        bYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yes();
            }
        });

        // no
        bNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                no();
            }
        });
    }

    // initializes Firebase data, attaches Firebase listeners
    private void initFirebase () {
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user == null) {
                    signIn();
                }
            }
        };

        // listens for 'count' changes
        dr.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if (documentSnapshot.exists()) {
                    int heardCount      = (int) Math.round(documentSnapshot.getDouble("heardCount"));
                    int liedCount       = (int) Math.round(documentSnapshot.getDouble("liedCount"));
                    int notHeardCount   = (int) Math.round(documentSnapshot.getDouble("notHeardCount"));

                    updateHeardCount(heardCount);
                    updateLiedCount(liedCount);
                    updateNotHeardCount(notHeardCount);
                }
            }
        });
    }

    // signs the user in through Firebase anonymous auth
    private void signIn () {
        mAuth.signInAnonymously()
                .addOnSuccessListener(
                    new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        // successfully signed in
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Failed to sign in.", Toast.LENGTH_LONG).show();
                    }
                });
    }

    // if user actually heard about the McDonald's app
    private void updateHeardCount (int heardCount) {
        tvHeard.setText(String.format(Locale.getDefault(), "%s %d", getString(R.string.tvHeard), heardCount));
    }

    // if user lied about hearing about the McDonald's app
    private void updateLiedCount (int liedCount) {
        tvLies.setText(String.format(Locale.getDefault(), "%s %d", getString(R.string.tvLies), liedCount));
    }

    // if user actually hasn't heard about the McDonald's app
    private void updateNotHeardCount (int notHeardCount) {
        tvNotHeard.setText(String.format(Locale.getDefault(), "%s %d", getString(R.string.tvNotHeard), notHeardCount));
    }

    // heard about the McDonald's's app
    private void yes () {
        if (Utils.isAppInstalled(this, getString(R.string.mcpackage))) {
            // has McDonald's app installed
            heard();
            launchMcDonaldsApp();
        } else {
            // lied, doesn't have McDonald's app installed
            lied();
            launchPlayStore();
        }
    }

    // didn't hear about the McDonald's's app
    private void no () {
        if (Utils.isAppInstalled(this, getString(R.string.mcpackage))) {
            // lied, has McDonald's app installed
            lied();
            launchMcDonaldsApp();
        } else {
            // doesn't have McDonald's app installed
            notHeard();
            launchPlayStore();
        }
    }

    private void heard () {
        // TODO
    }

    private void lied () {
        // TODO
    }

    private void notHeard () {
        // TODO
    }

    // opens McDonald's app link in the play store
    private void launchPlayStore () {
        Toast.makeText(this, "Launching Play Store...", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + getString(R.string.mcpackage)));
        startActivity(intent);
    }

    private void launchMcDonaldsApp () {
        Toast.makeText(this, "Launching Ron's...", Toast.LENGTH_SHORT).show();

        Intent intent = getPackageManager().getLaunchIntentForPackage(getString(R.string.mcpackage));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
