package com.mohammed.tcmc.ComerBem.activity;

import android.annotation.SuppressLint;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

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

import static com.mohammed.tcmc.ComerBem.R.id.email;
import static com.mohammed.tcmc.ComerBem.R.id.nameText;

public class EditProfileActivity extends AppCompatActivity {
    String imgurl;
    EditText nameTextEP, emailEP, numberphoneEP, DataEP, SexoEP, CidadeEP, EstadoEP;
    private FirebaseAuth autenticacao;
    private DatabaseReference mDatabase;
    String emailId;
    Usuario usuario;
    String idUsuario;
    ImageView edit_imge_proEP;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setContentView(R.layout.activity_edit_profile);

        nameTextEP = (EditText) findViewById(nameText);
        emailEP = (EditText) findViewById(R.id.emailEP);
        numberphoneEP = (EditText) findViewById(R.id.numberphoneEP);
        edit_imge_proEP = (ImageView) findViewById(R.id.edit_imge_proEP);

        setUserInfo();

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

    }

    public void setUserInfo() {
        Usuario user = new Usuario();
        Map<String, String> userInfos = UserStorage.getUserInfo(getBaseContext());
        if (userInfos.get("USER_NAME") != null) {
            nameTextEP.setText(userInfos.get("USER_NAME"));
        }
        if (userInfos.get("USER_EMAIL") != null) {
            idUsuario = userInfos.get("USER_EMAIL");
            emailEP.setText(userInfos.get("USER_EMAIL"));
        }
        if (userInfos.get("USER_PHONE") != null) {
            numberphoneEP.setText(userInfos.get("USER_PHONE"));
        }

        if (userInfos.get("IMG_URL") != null) {

            Picasso.with(this)
                    .load(userInfos.get("IMG_URL"))
                    .placeholder(ContextCompat.getDrawable(this, R.drawable.quatro))
                    .into(edit_imge_proEP);
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
                            user.setTelefone(child.child("telefone").getValue().toString());
                            user.setImgurl(child.child("imgurl").getValue().toString());
                        }

                        //UserStorage.saveUserInfo(getApplicationContext(), user.getNome(), user.getEmail(),user.getImgurl());


                        // do something with this user or save it
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                        Log.w("->", "loadPost:onCancelled", databaseError.toException());

                    }
                });

    }

    public void update(View view) {

        String nome, telefone;

        nome = nameTextEP.getText().toString();
        telefone = numberphoneEP.getText().toString();



        Usuario usuario = new Usuario();
        usuario.setIdUsuario(emailId);
        emailId = codificarBase64(emailId);

        //email =
        mDatabase.child("usuarios").child(emailId).setValue(usuario);

        String teste = decodificarBase64(emailId);

        onBackPressed();
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }


    private String codificarBase64(String texto) {
        return Base64.encodeToString(texto.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");
    }

    private String decodificarBase64(String textoCodificado) {
        return new String(Base64.decode(textoCodificado, Base64.DEFAULT));
    }

    public Usuario getUsuario() {
        return usuario;
    }
}