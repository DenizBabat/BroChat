package com.babat.deniz.brochat;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    //commit test
    private static int SIGN_IN_REQUEST_CODE = 1;
    public RelativeLayout profils;
    public Profil reciever = new Profil();
    public Intent intent;
    public Profil profil = new Profil();
    private  int RESULT;

    private String PROFIL = "PROFILS";
    FirebaseDatabase db;
    DatabaseReference dbRef;
    DatabaseReference dbRefNew;
    private ArrayList<String> dbProfilList = new ArrayList<>();
  //  private SQLDatabase mydb = new SQLDatabase(MainActivity.this);
    private ListView listView;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //önceden kayıt varsa tekrar kayıt oluşturulamıyor.
        if(requestCode == SIGN_IN_REQUEST_CODE)
        {
            RESULT = resultCode;
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

    /**
     * null sorunu var userı çekemedim.
     * @param view
     */
    public void RefreshOnClick(View view) {

        final String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        final String user = FirebaseAuth.getInstance().getCurrentUser().getDisplayName().trim();

        final String temp = user+"/"+email;

      //  Toast.makeText(getApplicationContext(), "R=>"+temp, Toast.LENGTH_SHORT).show();

        final boolean[] addflag = {false};
        if (RESULT == RESULT_OK) {
            db = FirebaseDatabase.getInstance();
            dbRef = db.getReference(PROFIL);

            dbRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Iterable<DataSnapshot> keys = dataSnapshot.getChildren();
                    boolean isAdd = false;
                    for (int i = 0; i <50000 ; i++) {

                    }
                    for (DataSnapshot key:keys)
                    {
                        String str = key.getValue().toString();
                        if (str.equals(temp) || str.equals(null)) {
                            isAdd = true;
                            Toast.makeText(getApplicationContext(), "REFRESH ADDED", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (!isAdd){
                        String key = dbRef.push().getKey();
                        dbRefNew = db.getReference(PROFIL + "/" + key);
                        dbRefNew.setValue(user+"/"+email);
                        isAdd = true;
                        Toast.makeText(getApplicationContext(), "REFRESH ADD", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }

            });
        }
        else {
            db = FirebaseDatabase.getInstance();
            final DatabaseReference dbProfils = db.getReference(PROFIL);

            listView = findViewById(R.id.profilview);
            final ArrayAdapter<String> adapter=new ArrayAdapter<String>
                    (this, android.R.layout.simple_list_item_1, android.R.id.text1, dbProfilList);
            dbProfils.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Iterable<DataSnapshot> keys = dataSnapshot.getChildren();
                    for (DataSnapshot key:keys){
                       // Toast.makeText(getApplicationContext(), key.getValue().toString(), Toast.LENGTH_SHORT).show();
                        String prf = key.getValue().toString();
                        if (!prf.equals(temp)) {
                            dbProfilList.add(prf);

                            addflag[0] = true;
                        }
                    }
                    listView.setAdapter(adapter);


                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                            Intent intent = new Intent(MainActivity.this, MessageActivity.class);
                            String pos = ((TextView) view).getText().toString();
                            StringTokenizer token  = new StringTokenizer(pos,"/");
                            String reciever = token.nextToken();
                            String email = token.nextToken();

                            String sender = FirebaseAuth.getInstance().getCurrentUser().getDisplayName().trim();

                            profil.setSender(sender);
                            profil.setReciever(reciever);

                            intent.putExtra("btn", profil);
                            //Toast.makeText(getApplicationContext(),sender,Toast.LENGTH_SHORT).show();

                            startActivity(intent);
                        }
                    });


                    if (addflag[0])
                        Toast.makeText(getApplicationContext(), "REFRESH ADDED2", Toast.LENGTH_SHORT).show();
                    else {
                        Toast.makeText(getApplicationContext(), "ALREADY REFRESH", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }



    }
//--------------------------------------------------------------------------------------------------
}
