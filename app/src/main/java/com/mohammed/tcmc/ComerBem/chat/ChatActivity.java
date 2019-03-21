package com.mohammed.tcmc.ComerBem.chat;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.mohammed.tcmc.ComerBem.R;
import com.mohammed.tcmc.ComerBem.activity.SendImageActivity;
import com.mohammed.tcmc.ComerBem.adapters.MessageAdapter;
import com.mohammed.tcmc.ComerBem.models.Message;
import com.mohammed.tcmc.ComerBem.models.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity implements ValueEventListener {

    EditText ediText;
    ImageButton bouton;
    RecyclerView recyclerView;
    CardView cardView;
    MessageAdapter mMessageAdapter;
    DatabaseReference mDatabaseReference;
    DatabaseReference newDbRef;
    LinearLayoutManager linearLayoutManager;
    Map<String, String> userInfos;
    static Context context;
    static AlertDialog.Builder alertDialogBuilder;


    ImageView imageButton;
    private StorageReference mStorage;
    private Uri img_uri;
    private ProgressDialog progressDialog;
    private static String imgurl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ediText = (EditText) findViewById(R.id.inputEditText);
        ediText = (EditText) findViewById(R.id.inputEditText);
        bouton = (ImageButton) findViewById(R.id.sendButton);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        userInfos = UserStorage.getUserInfo(getBaseContext());
        context = getApplicationContext();

        alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(
                ChatActivity.this, R.style.Dialog));

        List<Message> dataList = new ArrayList<>();

        mMessageAdapter = new MessageAdapter(dataList, userInfos.get("USER_EMAIL"));

        connection();

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mMessageAdapter);


        bouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
                ediText.setText("");
                recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
            }
        });

    }


    public void connection() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabaseReference = database.getReference(getString(R.string.patepabo));
        mDatabaseReference.addValueEventListener(this);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        List<Message> items = new ArrayList<>();
        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
            Message message = postSnapshot.getValue(Message.class);
            message.setKey(postSnapshot.getKey());
            items.add(message);
        }
        mMessageAdapter.setDatas(items);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    public void sendMessage() {
        newDbRef = mDatabaseReference.push();
        newDbRef.setValue(
                new Message(ediText.getText().toString(), userInfos.get("USER_NAME")
                        , userInfos.get("USER_EMAIL"), userInfos.get("IMG_URL"), null));

    }

    public void imageButtonClick(View view) {

        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, 200);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 200 && data != null) {
            Uri imagerUri = data.getData();
            //imageViewcc.setImageURI(imagerUri);
            img_uri = imagerUri;
            //Toast.makeText(getApplicationContext(), imagerUri.toString(), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, SendImageActivity.class);
            intent.putExtra("imguri", img_uri.toString());
            startActivity(intent);
        }
    }


}
