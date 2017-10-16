package net.magnusfrater.haveyouheard;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // views
    private TextView tvQuestion;
    private Button bYes;
    private Button bNo;

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
    }

    private void initViews () {
        tvQuestion  = (TextView)    findViewById(R.id.tvQuestion);
        bYes        = (Button)      findViewById(R.id.bYes);
        bNo         = (Button)      findViewById(R.id.bNo);
    }

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

    // heard about the McDonald's's app
    private void yes () {
        if (Utils.isAppInstalled(this, "com.mcdonalds.app")) {
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
        if (Utils.isAppInstalled(this, "com.mcdonalds.app")) {
            // lied, has McDonald's app installed
            lied();
            launchMcDonaldsApp();
        } else {
            // doesn't have McDonald's app installed
            notHeard();
            launchPlayStore();
        }
    }

    // opens McDonald's app link in the play store
    private void launchPlayStore () {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=com.mcdonalds.app"));
        startActivity(intent);
    }

    private void launchMcDonaldsApp () {
        Intent intent = getPackageManager().getLaunchIntentForPackage("com.mcdonalds.app");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    // if user lied about hearing about the McDonald's app
    private void lied () {
        liedCount++;
    }

    // if user actually heard about the McDonald's app
    private void heard () {
        heardCount++;
    }

    // if user actually hasn't heard about the McDonald's app
    private void notHeard () {
        notHeardCount++;
    }
}
