package com.mohammed.tcmc.ComerBem.activity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mohammed.tcmc.ComerBem.R;
import com.mohammed.tcmc.ComerBem.models.Message;
import com.mohammed.tcmc.ComerBem.models.UserStorage;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Map;

public class ImageComent extends AppCompatActivity {
    ImageView imgehome,sendButton;
    EditText inputEditText;
    private Uri img_uri;
    private StorageReference mStorage;
    private DatabaseReference mDatabase;
    DatabaseReference newDbRef;
    private ProgressDialog progressDialog;
    private static String imgurl;
    Map<String, String> userInfos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_coment);


    img_uri= Uri.parse( getIntent().getExtras().getString("imguri") );

    imgehome = (ImageView) findViewById( R.id.imgehome2 );
    sendButton = (ImageView) findViewById( R.id.sendButton );
    inputEditText = (EditText) findViewById( R.id.inputEditText );

    mDatabase = FirebaseDatabase.getInstance().getReference();
    mStorage = FirebaseStorage.getInstance().getReference();
    progressDialog = new ProgressDialog(this);
    userInfos = UserStorage.getUserInfo(getBaseContext());
        Picasso.with(getApplicationContext()).load(img_uri).into(imgehome);
    connection();

}
    public void salvarUsuarioDB(){

        progressDialog.setMessage(getString(R.string.Carregando));
        progressDialog.show();

        Calendar calendar = Calendar.getInstance();
        StorageReference filepath = mStorage.child("Users").child("img_" + calendar.getTimeInMillis());

        filepath.putFile(img_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                imgurl = taskSnapshot.getDownloadUrl().toString();
                saveToDataBase();
                Picasso.with(getApplicationContext()).load(taskSnapshot.getDownloadUrl()).into(imgehome);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //progressDialog.dismiss();
                Toast.makeText(ImageComent.this, getString(R.string.não_carregou), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void connection() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(getString(R.string.comments));

    }
    public void saveToDataBase(){
        newDbRef = mDatabase.push();
        newDbRef.setValue(
                new Message(inputEditText.getText().toString(), userInfos.get("USER_NAME")
                        , userInfos.get("USER_EMAIL"),userInfos.get("IMG_URL"),imgurl));

        finish();

    }

    public void sendImageClick(View view) {
        salvarUsuarioDB();
    }
}