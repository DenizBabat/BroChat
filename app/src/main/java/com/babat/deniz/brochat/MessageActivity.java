package com.babat.deniz.brochat;


import android.content.Intent;
import android.os.Parcelable;
import android.print.PrinterId;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.text.format.DateFormat;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;
import hani.momanii.supernova_emoji_library.Helper.EmojiconTextView;


public class MessageActivity extends AppCompatActivity {

    //commit test
    private static int SIGN_IN_REQUEST_CODE = 1;
    private FirebaseListAdapter<Profil> adapter;
    private ArrayAdapter<Profil> profAdap;
    RelativeLayout activity_main;

    //Add Emojicon
    EmojiconEditText emojiconEditText;
    ImageView emojiButton, submitButton;
    EmojIconActions emojIconActions;

    ChatMessage cm = new ChatMessage();
    Profil profil = new Profil();

    private final String user = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

    private String MESSAGE = "MESSAGES";
    FirebaseDatabase db;
    DatabaseReference dbRef;
    DatabaseReference dbRefNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        activity_main = (RelativeLayout) findViewById(R.id.activity_main);

        try {
            Intent intent = getIntent();

            profil = (Profil) intent.getExtras().getParcelable("btn");

            profil.setPk1(profil.getSender() + profil.getReciever());
            profil.setPk2(profil.getReciever() + profil.getSender());


            //Profilden profile giden messagelerı kontorl etmek için ekrana bir toast messaji debug i yapiliyor burada.
            Toast.makeText(getApplicationContext(), profil.getSender() + "<->" + profil.getReciever(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e("Err", e.getMessage());
        }


        //Add Emoji
        emojiButton = (ImageView) findViewById(R.id.emoji_button);
        submitButton = (ImageView) findViewById(R.id.submit_button);
        emojiconEditText = (EmojiconEditText) findViewById(R.id.emojicon_edit_text);
        emojIconActions = new EmojIconActions(getApplicationContext(), activity_main, emojiButton, emojiconEditText);
        emojIconActions.ShowEmojicon();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cm = new ChatMessage(emojiconEditText.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getEmail());
                profil.setCm(cm);

              //  db = FirebaseDatabase.getInstance();
             //   DatabaseReference dbRef = db.getReference(MESSAGE);
                String key = dbRef.push().getKey();
                DatabaseReference dbRefNew  = db.getReference(MESSAGE + "/" + key);
                dbRefNew.setValue(profil);

                // FirebaseDatabase.getInstance().getReference().push().setValue(profil);

                emojiconEditText.setText("");
                emojiconEditText.requestFocus();
            }
        });

        //Load content
        displayChatMessage();

    }


    private void displayChatMessage() {

       /* final String user = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        final String reciever = profil.getReciever();
        final String combine = user + reciever;

        final ListView listOfMessage = (ListView) findViewById(R.id.list_of_message);
        final ArrayList<Profil> list =new ArrayList<>();

        db = FirebaseDatabase.getInstance();
        final DatabaseReference dbmessages = db.getReference(MESSAGE);

        dbmessages.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> keys = dataSnapshot.getChildren();
                for (DataSnapshot key:keys){
                    list.add((Profil) key.getValue());
                }

              //  Toast.makeText(getApplicationContext(), list.get(0).getCm().getMessageText(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        listOfMessage.setAdapter(adapter);*/
    }
}
