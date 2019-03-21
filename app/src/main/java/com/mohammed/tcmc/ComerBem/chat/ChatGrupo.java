package com.mohammed.tcmc.ComerBem.chat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mohammed.tcmc.ComerBem.R;
import com.mohammed.tcmc.ComerBem.activity.ImageComent;
import com.mohammed.tcmc.ComerBem.adapters.CommentAdapter;
import com.mohammed.tcmc.ComerBem.model.Comment;
import com.mohammed.tcmc.ComerBem.models.UserStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChatGrupo extends AppCompatActivity {

    Bitmap bitmap;     // pfoto on text


    TextView nome_da_Receita;
    ImageView itemImageView;

    String Imgurl, id, Nome_da_ReceitaValue;
    EditText ediText;
    ImageButton bouton;
    RecyclerView recyclerView;
    private Uri img_uri;
    static Context context;
    CommentAdapter mMessageAdapter;
    DatabaseReference mDatabaseReference;
    DatabaseReference newDbRef;
    static AlertDialog.Builder alertDialogBuilder;
    LinearLayoutManager linearLayoutManager;

    Map<String, String> userInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_grupo);


        Imgurl = getIntent().getExtras().getString("imgurl");
        id = getIntent().getExtras().getString("id");
        Nome_da_ReceitaValue = getIntent().getExtras().getString("nome_da_Receita");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);


        itemImageView = (ImageView) findViewById(R.id.imgaeGropoGropo);
        nome_da_Receita = (TextView) findViewById(R.id.NomeDpGrupo);
        nome_da_Receita.setText(getIntent().getExtras().getString("nome_da_Receita"));


        userInfos = UserStorage.getUserInfo(getBaseContext());

        List<Comment> dataList = new ArrayList<>();

        mMessageAdapter = new CommentAdapter(dataList, userInfos.get("USER_EMAIL"));

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mMessageAdapter);

        connection();

        Picasso.with(this)
                .load(Imgurl)
                .placeholder(ContextCompat.getDrawable(this, R.drawable.chefs2))
                .into(itemImageView);

        ediText = (EditText) findViewById(R.id.inputEditText);
        bouton = (ImageButton) findViewById(R.id.sendButton);

        bouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
                ediText.setText("");
            }
        });

        final FirebaseDatabase database2 = FirebaseDatabase.getInstance();

        database2.getReference().child(getString(R.string.chat)).orderByChild("idProduct").equalTo(id)
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        List<Comment> items = new ArrayList<>();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Comment comment = postSnapshot.getValue(Comment.class);
                            comment.setKey(postSnapshot.getKey());
                            items.add(comment);
                        }
                        mMessageAdapter.setDatas(items);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


    }

    public void connection() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabaseReference = database.getReference(getString(R.string.chat));
        //mDatabaseReference.addValueEventListener(this);
    }

    public void sendMessage() {
        newDbRef = mDatabaseReference.push();
        Log.d("-->", id);
        newDbRef.setValue(
                new Comment(ediText.getText().toString(), userInfos.get("USER_NAME"), userInfos.get("USER_EMAIL"),
                        id, userInfos.get("IMG_URL")));
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
            Intent intent = new Intent(this, ImageComent.class);
            intent.putExtra("imguri", img_uri.toString());
            startActivity(intent);
        }
    }

    /**  public static void removeMessage(String messageKey) {
     FirebaseDatabase database = FirebaseDatabase.getInstance();
     DatabaseReference databaseRef = database.getReference("chat/messages");
     databaseRef.child(messageKey).removeValue();
     }

     public static void showAlertDialog(final String messageKey) {

     alertDialogBuilder.setTitle(R.string.Apagar_mensagem);
     alertDialogBuilder.setMessage(R.string.Tem_certeza_que_quer_apagar_esta_mensagem);
     alertDialogBuilder.setCancelable(false);

     alertDialogBuilder.setPositiveButton(R.string.Sim, new DialogInterface.OnClickListener() {
     public void onClick(DialogInterface dialog, int id) {

     removeMessage(messageKey);

     Toast toast = Toast.makeText(context, R.string.Apagar_mensagem, Toast.LENGTH_SHORT);
     View view = toast.getView();
     view.setBackgroundResource(R.drawable.toast);
     toast.setView(view);
     toast.show();
     }
     });
     alertDialogBuilder.setNegativeButton(R.string.Não, new DialogInterface.OnClickListener() {
     public void onClick(DialogInterface dialog, int id) {
     dialog.cancel();
     }
     });

     AlertDialog alertDialog = alertDialogBuilder.create();
     alertDialog.show();
     }**/


}


