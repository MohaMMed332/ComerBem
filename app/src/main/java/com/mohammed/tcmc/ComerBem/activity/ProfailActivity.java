package com.mohammed.tcmc.ComerBem.activity;


import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mohammed.tcmc.ComerBem.R;
import com.mohammed.tcmc.ComerBem.config.ConfiguracaoFirebase;
import com.mohammed.tcmc.ComerBem.model.Usuario;
import com.mohammed.tcmc.ComerBem.models.UserStorage;
import com.squareup.picasso.Picasso;

import java.util.Map;


public class ProfailActivity extends AppCompatActivity {

    ImageView foodImageView;
    TextView nameText, email, numberphone, EstadoP, CidadeP, DataNP, SexoP;


    private FirebaseAuth autenticacao;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profail);


        foodImageView = (ImageView) findViewById(R.id.imge_prof);
        nameText = (TextView) findViewById(R.id.nameText);
        numberphone = (TextView) findViewById(R.id.numberphone);
        email = (TextView) findViewById(R.id.email);

        SexoP = (TextView) findViewById(R.id.SexoP);
        DataNP = (TextView) findViewById(R.id.DataNP);
        CidadeP = (TextView) findViewById(R.id.CidadeP);
        EstadoP = (TextView) findViewById(R.id.EstadoP);


        setUserInfo();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();


    }

    public void setUserInfo() {
        Usuario user = new Usuario();
        Map<String, String> userInfos = UserStorage.getUserInfo(getBaseContext());
        if (userInfos.get("USER_NAME") != null) {
            nameText.setText(userInfos.get("USER_NAME"));
        }
        if (userInfos.get("USER_EMAIL") != null) {
            email.setText(userInfos.get("USER_EMAIL"));
        }
        if (userInfos.get("USER_PHONE") != null) {
            numberphone.setText(userInfos.get("USER_PHONE"));
        }

        if (userInfos.get("USER_SEXO") != null) {
            SexoP.setText(userInfos.get("USER_SEXO"));
        }
        if (userInfos.get("USER_ANIVERSARIO") != null) {
            DataNP.setText(userInfos.get("USER_ANIVERSARIO"));
        }
        if (userInfos.get("USER_CIDADE") != null) {
            CidadeP.setText(userInfos.get("USER_CIDADE"));
        }
        if (userInfos.get("USER_ESTADO") != null) {
            EstadoP.setText(userInfos.get("USER_ESTADO"));
        }


        if (userInfos.get("IMG_URL") != null) {
            Picasso.with(this)
                    .load(userInfos.get("IMG_URL"))
                    .placeholder(ContextCompat.getDrawable(this, R.drawable.quatro))
                    .into(foodImageView);
        }

    }

    public void getUserInfo(String email) {

        FirebaseDatabase.getInstance().getReference()
                .child("usuarios").orderByChild("email").equalTo(email)
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Usuario user = dataSnapshot.getValue(Usuario.class);
                        Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
                        for (DataSnapshot child : iterable) {
                            user.setNome(child.child("nome").getValue().toString());
                            user.setEmail(child.child("email").getValue().toString());
                            user.setImgurl(child.child("imgurl").getValue().toString());
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                        Log.w("->", "loadPost:onCancelled", databaseError.toException());

                    }
                });

    }


    public void onClickEdit(View view) {
        Bundle bundle = new Bundle();

        bundle.putString("idUsuario", "123");

        Intent i = new Intent(this, EditProfileActivity.class);

        i.putExtras(bundle);
        startActivity(i);

    }
}