package com.mohammed.tcmc.ComerBem.FirebaseLogin;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mohammed.tcmc.ComerBem.activity.PrincipalActivity;
import com.mohammed.tcmc.ComerBem.config.ConfiguracaoFirebase;
 import com.mohammed.tcmc.ComerBem.model.Usuario;
import com.mohammed.tcmc.ComerBem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.mohammed.tcmc.ComerBem.models.UserStorage;

public class LoginActivity extends AppCompatActivity {


    private Button botaoEntrar;
    private EditText campoEmail, campoSenha;
    private Usuario usuario;
     private FirebaseAuth autenticacao;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final ProgressDialog progressDialog = new ProgressDialog(this);





        campoEmail = findViewById(R.id.editEmail);
        campoSenha = findViewById(R.id.editSenha);
        botaoEntrar = findViewById(R.id.buttonEntrar);



         botaoEntrar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override public void onClick(View v) {

                String textoEmail = campoEmail.getText().toString();
                String textoSenha = campoSenha.getText().toString();

                if ( !textoEmail.isEmpty() ){
                    if ( !textoSenha.isEmpty() ){

                        usuario = new Usuario();
                        usuario.setEmail( textoEmail );
                        usuario.setSenha( textoSenha );
                        validarLogin(); }
                        else { Toast.makeText(LoginActivity.this,
                            R.string.Preencher_a_senha,
                            Toast.LENGTH_SHORT).show(); } }
                            else { Toast.makeText(LoginActivity.this,
                        R.string.Preencher_o_email,
                        Toast.LENGTH_SHORT).show();}}});
    }



    public void validarLogin(){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if ( task.isSuccessful() ){
                    getUserInfo(usuario.getEmail());

                }else {

                    String excecao = "";
                    try {
                        throw task.getException();
                    }catch ( FirebaseAuthInvalidUserException e ) {
                        excecao = getString(R.string.Usuário_não_cadastrado);
                    }catch ( FirebaseAuthInvalidCredentialsException e ){
                        excecao = getString(R.string.Email_e_senha_não_correspondem_a_um_usuário_cadastrado);
                    }catch (Exception e){
                        excecao = getString(R.string.Erro_ao_cadastrar_usuário)  + e.getMessage();e.printStackTrace(); }

                    Toast.makeText(LoginActivity.this,
                            excecao,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void getUserInfo(String email){

        FirebaseDatabase.getInstance().getReference()
                .child("usuarios").orderByChild("email").equalTo(email)
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Usuario user = dataSnapshot.getValue(Usuario.class);
                        Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
                        for(DataSnapshot child : iterable){
                            user.setNome(child.child("nome").getValue().toString());
                            user.setEmail(child.child("email").getValue().toString());
                            user.setImgurl(child.child("imgurl").getValue().toString());
                            user.setTelefone(child.child("telefone").getValue().toString());





                        }

                        UserStorage.saveUserInfo(getApplicationContext(), user.getNome(), user.getEmail(), user.getImgurl(),user.getTelefone());
                        abrirTelaPrincipal();

                        // do something with this user or save it
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                        Log.w("->", "loadPost:onCancelled", databaseError.toException());

                    }
                });

    }


    public void abrirTelaPrincipal(){
        startActivity(new Intent(this, PrincipalActivity.class));
        finish();
    }

     public void btCadastrar(View view){
        startActivity(new Intent(this, CadastroActivity.class));
    }





}
