package com.babat.deniz.brochat;


import android.content.Intent;
import android.print.PrinterId;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.google.firebase.database.FirebaseDatabase;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;
import hani.momanii.supernova_emoji_library.Helper.EmojiconTextView;


public class MessageActivity extends AppCompatActivity {

    //commit test
    private static int SIGN_IN_REQUEST_CODE = 1;
    private FirebaseListAdapter<Profil> adapter;
    RelativeLayout activity_main;

    //Add Emojicon
    EmojiconEditText emojiconEditText;
    ImageView emojiButton,submitButton;
    EmojIconActions emojIconActions;

    ChatMessage cm = new ChatMessage();
    Profil profil = new Profil();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity_main = (RelativeLayout)findViewById(R.id.activity_main);

        try {
            Intent intent = getIntent();

            profil = (Profil) intent.getExtras().getParcelable("btn");

            //Profilden profile giden messagelerı kontorl etmek için ekrana bir toast messaji debug i yapiliyor burada.
            //Toast.makeText(getApplicationContext(), profil.getSender() + "<->" + profil.getReciever(), Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Log.e("Err", e.getMessage());
        }


        //Add Emoji
        emojiButton = (ImageView)findViewById(R.id.emoji_button);
        submitButton = (ImageView)findViewById(R.id.submit_button);
        emojiconEditText = (EmojiconEditText)findViewById(R.id.emojicon_edit_text);
        emojIconActions = new EmojIconActions(getApplicationContext(),activity_main,emojiButton,emojiconEditText);
        emojIconActions.ShowEmojicon();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cm =new ChatMessage(emojiconEditText.getText().toString(),FirebaseAuth.getInstance().getCurrentUser().getEmail());
                profil.setCm(cm);

                FirebaseDatabase.getInstance().getReference().push().setValue(profil);

                emojiconEditText.setText("");
                emojiconEditText.requestFocus();
            }
        });

        //Load content
        displayChatMessage();

    }



    private void displayChatMessage() {

        final String user = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

        ListView listOfMessage = (ListView)findViewById(R.id.list_of_message);

        adapter = new FirebaseListAdapter<Profil>(this,Profil.class,
                R.layout.list_item,
                FirebaseDatabase.getInstance().getReference())
        {
            @Override
            protected void populateView(View v, Profil prof, int position) {

                //Get references to the views of list_item.xml
                TextView messageText, messageUser, messageTime;
                messageText = (EmojiconTextView) v.findViewById(R.id.message_text);
                messageUser = (TextView) v.findViewById(R.id.message_user);
                messageTime = (TextView) v.findViewById(R.id.message_time);

                String reciever = prof.getReciever();

                if (user.equals(reciever)) {

                    messageText.setText(prof.getCm().getMessageText());
                    messageUser.setText(prof.getCm().getMessageUser());
                    messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", prof.getCm().getMessageTime()));

                }else {
                    messageText.setText(null);
                    messageUser.setText(null);
                    messageTime.setText(null);
                }

            }
        };
        listOfMessage.setAdapter(adapter);

    }
}
