package com.mohammed.tcmc.ComerBem.edit_activities;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mohammed.tcmc.ComerBem.R;
import com.squareup.picasso.Picasso;

public class EditProductActivity extends AppCompatActivity {



    String idValue,Nome_da_ReceitaValue,IngredientesValue,IngredientesValue2,IngredientesValue3,ModoValue,ModoValue2,ModoValue3;

    private DatabaseReference mDatabase;
    TextInputEditText nome_da_Receita,ingredientes,ingredientes2,ingredientes3, modo,modo2,modo3;
    Bitmap bitmap;     // pfoto on text
    private static String imgurl;
    ImageView show_Image;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);


        mDatabase = FirebaseDatabase.getInstance().getReference();


        idValue=getIntent().getExtras().getString("id");
        Nome_da_ReceitaValue=getIntent().getExtras().getString("nome_da_Receita");
        IngredientesValue=getIntent().getExtras().getString("ingredientes");
        IngredientesValue2=getIntent().getExtras().getString("ingredientes2");
        IngredientesValue3=getIntent().getExtras().getString("ingredientes3");
        ModoValue=getIntent().getExtras().getString("modo");
        ModoValue2=getIntent().getExtras().getString("modo2");
        ModoValue3=getIntent().getExtras().getString("modo3");


        show_Image=(ImageView) findViewById(R.id.update_image );
        nome_da_Receita=(TextInputEditText) findViewById(R.id.update_name);
        ingredientes=(TextInputEditText)findViewById(R.id.update_Ingredientes);
        ingredientes2=(TextInputEditText)findViewById(R.id.update_Ingredientes2);
        ingredientes3=(TextInputEditText)findViewById(R.id.update_Ingredientes3);
        modo=(TextInputEditText)findViewById(R.id.update_Modo);
        modo2=(TextInputEditText)findViewById(R.id.update_Modo2);
        modo3=(TextInputEditText)findViewById(R.id.update_Modo3);


        nome_da_Receita.setText(Nome_da_ReceitaValue);
        ingredientes.setText(IngredientesValue);
        ingredientes2.setText(IngredientesValue2);
        ingredientes3.setText(IngredientesValue3);
        modo.setText(ModoValue);
        modo2.setText(ModoValue2);
        modo3.setText(ModoValue3);


        Picasso.with(this)
                .load(imgurl)
                .placeholder(ContextCompat.getDrawable(this,R.drawable.png_natal))
                .into(show_Image);

    }


    public void update(View view) {

        String nome_da_ReceitaText,ingredientesText,ingredientesText2,ingredientesText3,modoText,modoText2,modoText3,commentText;

        nome_da_ReceitaText=nome_da_Receita.getText().toString();
        ingredientesText=ingredientes.getText().toString();
        ingredientesText2=ingredientes2.getText().toString();
        ingredientesText3=ingredientes3.getText().toString();
        modoText=modo.getText().toString();
        modoText2=modo2.getText().toString();
        modoText3=modo3.getText().toString();

        Product product=new Product(imgurl,nome_da_ReceitaText,ingredientesText
                ,ingredientesText2,ingredientesText3,modoText,modoText2,modoText3);
        product.setId(idValue);

        //email =
        mDatabase.child(getString(R.string.Word)).child(idValue).setValue(product);

        onBackPressed();
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
