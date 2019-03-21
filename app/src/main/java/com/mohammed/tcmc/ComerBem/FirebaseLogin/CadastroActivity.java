package com.mohammed.tcmc.ComerBem.FirebaseLogin;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mohammed.tcmc.ComerBem.R;
import com.mohammed.tcmc.ComerBem.config.ConfiguracaoFirebase;
import com.mohammed.tcmc.ComerBem.helper.Base64Custom;
import com.mohammed.tcmc.ComerBem.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class CadastroActivity extends AppCompatActivity {
    TextView textbutn;
    private EditText Senha, CSenha, campoEmail, CNome, campoTelefone;
    private Button botaoCadastrar;
    private FirebaseAuth autenticacao;
    private Usuario usuario;
    private DatabaseReference mDatabase;
    ImageView imagV;
    private Uri img_uri;
    private StorageReference mStorage;
    private static String imgurl;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        img_uri = null;
        imagV = findViewById(R.id.Cadastro_show_Image);
        CNome = findViewById(R.id.CNome);
        campoEmail = findViewById(R.id.Cadastro_editEmail);
        Senha = findViewById(R.id.Senha);
        CSenha = findViewById(R.id.CSenha);
        campoTelefone = findViewById(R.id.Cadastro_editTelefone);
        botaoCadastrar = findViewById(R.id.Cadastro_buttonCadastrar);
        textbutn = findViewById(R.id.Cadastro_login);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance().getReference();
        progressDialog = new ProgressDialog(this);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textoNome = CNome.getText().toString().trim();
                String textoEmail = campoEmail.getText().toString().trim();
                String telefone = campoTelefone.getText().toString().trim();
                String textoSenha = Senha.getText().toString().trim();
                String textoCSenha = CSenha.getText().toString().trim();

                if (!textoNome.isEmpty()) {
                    if (!textoEmail.isEmpty()) {
                        if (!telefone.isEmpty()) {
                            if (!textoSenha.isEmpty()) {
                                if (!textoCSenha.isEmpty()) {
                                    if (textoSenha.equals( textoCSenha)) {

                                        usuario = new Usuario();
                                        usuario.setNome(textoNome);
                                        usuario.setEmail(textoEmail);
                                        usuario.setSenha(textoSenha);
                                        usuario.setTelefone(telefone);
                                        cadastrarUsuario();


                                    } else
                                        Toast.makeText(CadastroActivity.this,
                                                R.string.Error_Conf_senha,
                                                Toast.LENGTH_SHORT).show();
                                } else
                                    Toast.makeText(CadastroActivity.this,
                                            R.string.Confirma_a_senha,
                                            Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(CadastroActivity.this,
                                        R.string.Preencher_a_senha,
                                        Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(CadastroActivity.this,
                                    R.string.Preencher_o_telefone,
                                    Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(CadastroActivity.this,
                                R.string.Preencher_o_email,
                                Toast.LENGTH_SHORT).show();

                } else Toast.makeText(CadastroActivity.this,
                        R.string.Preencher_o_nome,
                        Toast.LENGTH_SHORT).show();

            }
        });
    }


    public void cadastrarUsuario() {

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {


                    progressDialog.setMessage(getString(R.string.Carregando));
//                    progressDialog.show();

                    salvarUsuarioDB();

                } else {
                    String excecao = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        excecao = getString(R.string.Digite_uma_senha_mais_forte);
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        excecao = getString(R.string.Por_favor_digite_um_email_válido);
                    } catch (FirebaseAuthUserCollisionException e) {
                        excecao = getString(R.string.Esta_conta_já_foi_cadastrada);
                    } catch (Exception e) {
                        excecao = getString(R.string.Erro_ao_cadastrar) + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(CadastroActivity.this, excecao, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void salvarUsuarioDB() {

        Calendar calendar = Calendar.getInstance();

        if (img_uri != null) {
            StorageReference filepath = mStorage.child("Users").child("img_" + calendar.getTimeInMillis());
            filepath.putFile(img_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {


                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    imgurl = taskSnapshot.getDownloadUrl().toString();
                    saveToDataBase();
                    Picasso.with(getApplicationContext()).load(taskSnapshot.getDownloadUrl()).into(imagV);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //progressDialog.dismiss();
                    Toast.makeText(CadastroActivity.this, R.string.Não_pod_carregar_este_tamanho_de_imagem, Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            saveToDataBase();
        }

    }


    public void saveToDataBase() {
        if (img_uri != null) {
            usuario.setImgurl(imgurl);
        } else {
            usuario.setImgurl("https://firebasestorage.googleapis.com/v0/b/appcomerbem.appspot.com/o/Users%2Fum.png?alt=media&token=7ab7842e-5201-4ffe-9543-bef58863267c");
        }
        String idUsuario = Base64Custom.codificarBase64(usuario.getEmail());
        usuario.setIdUsuario(idUsuario);
        usuario.salvar();
        autenticacao.signOut();
        finish();
        startActivity(new Intent(this, LoginActivity.class));

    }

    public void gologin(View view) {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }


    public void imageclick(View view) {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, 200);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 200 && data != null) {
            Uri imagerUri = data.getData();
            imagV.setImageURI(imagerUri);
            img_uri = imagerUri;
            Toast.makeText(getApplicationContext(), imagerUri.toString(), Toast.LENGTH_LONG).show();
        }
    }


    public void btEntrar(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }


}
