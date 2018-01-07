package com.babat.deniz.brochat;


import android.content.Context;
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
import android.widget.Adapter;
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

    private ArrayAdapter<Profil> profAdap;
    RelativeLayout activity_main;
    Context context = MessageActivity.this;

    //Add Emojicon
    EmojiconEditText emojiconEditText;
    ImageView emojiButton, submitButton;
    EmojIconActions emojIconActions;

    ChatMessage cm = new ChatMessage();
    Profil profil = new Profil();

    private final String user = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
    private final String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

    private String MESSAGE = "MESSAGES";
    FirebaseDatabase db;
    DatabaseReference dbRef;
    DatabaseReference dbRefNew;
    FirebaseListAdapter<Profil> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        activity_main = (RelativeLayout) findViewById(R.id.activity_main);



        //Toast.makeText(getApplicationContext(), "****"+FirebaseAuth.getInstance().getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();

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

                cm = new ChatMessage(emojiconEditText.getText().toString(), email);
                profil.setCm(cm);

                db = FirebaseDatabase.getInstance();
                DatabaseReference dbRef = db.getReference(MESSAGE);
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

        ListView listOfMessage = (ListView)findViewById(R.id.list_of_message);
        adapter = new FirebaseListAdapter<Profil>(this,Profil.class,
                R.layout.list_item,
                FirebaseDatabase.getInstance().getReference(MESSAGE))
        {
            @Override
            protected void populateView(View v, Profil prf, int position) {

                //Get references to the views of list_item.xml
                TextView messageText, messageUser, messageTime;
                messageText = (EmojiconTextView) v.findViewById(R.id.message_text);
                messageUser = (TextView) v.findViewById(R.id.message_user);
                messageTime = (TextView) v.findViewById(R.id.message_time);

                messageText.setText(prf.getCm().getMessageText());
                messageUser.setText(prf.getCm().getMessageUser());
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", prf.getCm().getMessageTime()));



                Toast.makeText(getApplicationContext(),messageText.toString(),Toast.LENGTH_SHORT).show();

            }
        };
        listOfMessage.setAdapter(adapter);
    }
}
