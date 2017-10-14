package net.magnusfrater.haveyouheard;

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
        Toast.makeText(this, "Yes, I heard about the McDonald's's app!", Toast.LENGTH_LONG).show();
    }

    // didn't hear about the McDonald's's app
    private void no () {
        Toast.makeText(this, "No, I haven't heard about the McDonald's's app...", Toast.LENGTH_LONG).show();
    }
}
