package com.goyourfly.kmeanalgorithm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this,"Touch screen to add k point.",Toast.LENGTH_LONG).show();
        final KMeanView kMeanView = findViewById(R.id.kMeanView);
        kMeanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kMeanView.addKPoint();
            }
        });
    }

}
