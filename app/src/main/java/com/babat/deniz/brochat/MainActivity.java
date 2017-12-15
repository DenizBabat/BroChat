package com.babat.deniz.brochat;

import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;


public class MainActivity extends AppCompatActivity {

    //commit test
    private static int SIGN_IN_REQUEST_CODE = 1;
    private FirebaseListAdapter<Profil> adapter;
    public RelativeLayout profils;
    public Profil reciever = new Profil();
    public Intent intent;
    public Profil profil = new Profil();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SIGN_IN_REQUEST_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                Snackbar.make(profils,"Successfully signed in.Welcome!", Snackbar.LENGTH_SHORT).show();

            }
            else{
                Snackbar.make(profils,"We couldn't sign you in.Please try again later", Snackbar.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profils);

        profils = (RelativeLayout)findViewById(R.id.profil);


        //Check if not sign-in then navigate Signin page
        if(FirebaseAuth.getInstance().getCurrentUser() == null)
        {
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(),SIGN_IN_REQUEST_CODE);
        }
        else
        {
            Snackbar.make(profils,"Welcome "+FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), Snackbar.LENGTH_LONG).show();
        }

        Button p1 = findViewById(R.id.p1);
        Button p2 = findViewById(R.id.p2);

        p1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getBaseContext(), MessageActivity.class);
                String sender = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                String reciever = "deniz";
                profil.setReciever(reciever);
                profil.setSender(sender);
               // Toast.makeText(getBaseContext(), "++++geldi+++++",Toast.LENGTH_LONG).show();
                intent.putExtra("btn", (Parcelable) profil);
                startActivity(intent);
            }
        });

        p2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getBaseContext(), MessageActivity.class);
                String sender = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                String reciever = "babat";
                profil.setReciever(reciever);
                profil.setSender(sender);

                intent.putExtra("btn", profil);
                startActivity(intent);
            }
        });


    }

//--------------------------------------------------------------------------------------------------

    /**
     * Sign out requerments
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_sign_out)
        {
            AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Snackbar.make(profils,"You have been signed out.", Snackbar.LENGTH_SHORT).show();
                    finish();
                }
            });
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }
//--------------------------------------------------------------------------------------------------
}
