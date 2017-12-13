package com.babat.deniz.brochat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by deniz on 13.12.2017.
 */

public class ProfilActivity extends AppCompatActivity {
    Profil reciever = new Profil();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil);

        Button p1 = findViewById(R.id.p1);
        Button p2 = findViewById(R.id.p2);

        p1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MessageActivity.class);
                reciever.setSender(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                reciever.setReciever("p1");
                intent.putExtra("extdata", (Parcelable) reciever);
                startActivity(intent);
            }
        });

        p2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MessageActivity.class);
                reciever.setSender(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                reciever.setReciever("p2");
                intent.putExtra("extdata", (Parcelable) reciever);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
