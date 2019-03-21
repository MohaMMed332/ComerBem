package com.mohammed.tcmc.ComerBem.Publicar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.mohammed.tcmc.ComerBem.edit_activities.Product;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class AddProductActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    ImageView imageView;
    private StorageReference mStorage;
    private Uri img_uri;
    private ProgressDialog progressDialog;
    private static String imgurl;


    TextInputEditText nome_da_Receita, ingredientes, ingredientes2, ingredientes3, modo, modo2, modo3;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        img_uri = null;
        nome_da_Receita = findViewById(R.id.add_name);
        ingredientes = findViewById(R.id.add_Ingredientes);
        ingredientes2 = findViewById(R.id.add_Ingredientes2);
        ingredientes3 = findViewById(R.id.add_Ingredientes3);
        modo = findViewById(R.id.add_Modo);
        modo2 = findViewById(R.id.add_Modo2);
        modo3 = findViewById(R.id.add_Modo3);
        imageView = findViewById(R.id.add_imge);


        mDatabase = FirebaseDatabase.getInstance().getReference();
        progressDialog = new ProgressDialog(this);
        mStorage = FirebaseStorage.getInstance().getReference();


        Picasso.with(this)
                .load(img_uri)
                .placeholder(ContextCompat.getDrawable(this, R.drawable.png_natal))
                .into(imageView);

    }

    public void imageView_click(View view) {

        ///open albom
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, 200);
        //open camera

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 200 && data != null) {
            Uri imagerUri = data.getData();
            imageView.setImageURI(imagerUri);
            img_uri = imagerUri;
            Toast.makeText(getApplicationContext(), imagerUri.toString(), Toast.LENGTH_LONG).show();
        }
    }


    public void add(View view) {
        Calendar calendar = Calendar.getInstance();

        progressDialog.setMessage(getString(R.string.Carregando));
        progressDialog.show();

        String nameText, ingredientesText, ingredientes2Text, ingredientes3Text, modoText, modo2Text, modo3Text, commentText;

        nameText = nome_da_Receita.getText().toString();
        ingredientesText = ingredientes.getText().toString();
        ingredientes2Text = ingredientes2.getText().toString();
        ingredientes3Text = ingredientes3.getText().toString();
        modoText = modo.getText().toString();
        modo2Text = modo2.getText().toString();
        modo3Text = modo3.getText().toString();

        if(img_uri==null){
            progressDialog.dismiss();
            Toast.makeText(AddProductActivity.this, R.string.inserir_image, Toast.LENGTH_SHORT).show();
            return;
        }

        if (nameText.isEmpty() || nameText.equals(" ")) {
            nome_da_Receita.setError(getString(R.string.Favor_preencher));
            return;
        }

        if (ingredientesText.isEmpty() || ingredientesText.equals(" ")) {
            ingredientes.setError(getString(R.string.Favor_preencher));
            return;
        }
        if (ingredientes2Text.isEmpty() || ingredientes2Text.equals(" ")) {
            ingredientes2.setError(getString(R.string.Favor_preencher));
            return;
        }

        if (ingredientes3Text.isEmpty() || ingredientes3Text.equals(" ")) {
            ingredientes3.setError(getString(R.string.Favor_preencher));
            return;
        }

        if (modoText.isEmpty() || modoText.equals(" ")) {
            modo.setError(getString(R.string.Favor_preencher));
            return;
        }
        if (modo2Text.isEmpty() || modo2Text.equals(" ")) {
            modo2.setError(getString(R.string.Favor_preencher));
            return;
        }
        if (modo3Text.isEmpty() || modo3Text.equals(" ")) {
            modo3.setError(getString(R.string.Favor_preencher));
            return;
        }


        StorageReference filepath = mStorage.child(getString(R.string.Word)).child("img_" + calendar.getTimeInMillis());

        filepath.putFile(img_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                progressDialog.dismiss();
                Toast.makeText(AddProductActivity.this, R.string.Upload_de_imagem, Toast.LENGTH_SHORT).show();
                Log.d("nome_da_Receita ---> ", nome_da_Receita.getText().toString());
                Log.d("imgurl ---> ", taskSnapshot.getDownloadUrl().toString());
                Log.d("ingredientes ---> ", ingredientes.getText().toString());
                Log.d("ingredientes2 ---> ", ingredientes2.getText().toString());
                Log.d("ingredientes3 ---> ", ingredientes3.getText().toString());
                Log.d("modo ---> ", modo.getText().toString());
                Log.d("modo2 ---> ", modo2.getText().toString());
                Log.d("modo3 ---> ", modo3.getText().toString());
                imgurl = taskSnapshot.getDownloadUrl().toString();
                Log.d("FUNCIONA ---> ", imgurl);
                Log.d("->", "Entrou");
                finish();
                Picasso.with(getApplicationContext()).load(taskSnapshot.getDownloadUrl()).into(imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(AddProductActivity.this, R.string.não_carregou, Toast.LENGTH_SHORT).show();
            }
        });


        Product product = new Product(imgurl, nameText, ingredientesText, ingredientes2Text, ingredientes3Text
                , modoText, modo2Text, modo3Text);

        String key = mDatabase.child(getString(R.string.Word)).push().getKey();
        product.setId(key);

        mDatabase.child(getString(R.string.Word)).child(key).setValue(product);

        nome_da_Receita.setText("");
        ingredientes.setText("");
        ingredientes2.setText("");
        ingredientes3.setText("");
        modo.setText("");
        modo2.setText("");
        modo3.setText("");

        Picasso.with(this)
                .load(imgurl)
                .placeholder(ContextCompat.getDrawable(this, R.drawable.png_natal))
                .into(imageView);


    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }


}
