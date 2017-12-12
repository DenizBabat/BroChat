package com.babat.deniz.brochat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by deniz on 13.12.2017.
 */

public class ProfilActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil);


    }

    public void clickProfil1(View view) {
        Button b = findViewById(R.id.p1);

        String str = (String) b.getText();

        Profil profil1 = new Profil();

        profil1.setSender(str);

        Intent mss=new Intent(getApplicationContext(), MainActivity.class);
    }
}
