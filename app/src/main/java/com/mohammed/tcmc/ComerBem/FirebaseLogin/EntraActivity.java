package com.mohammed.tcmc.ComerBem.FirebaseLogin;

import android.support.v7.app.AppCompatActivity;

public class EntraActivity extends AppCompatActivity {



   /** private FirebaseAuth autenticacao;
    private Usuario usuario;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entra);

            getAccount();



    }
    private void getAccount(){
        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(com.facebook.accountkit.Account account) {
                usuario = new Usuario ();

                String token = account.getId();
                String email = account.getEmail()==null?"":account.getEmail();
                String phone = account.getPhoneNumber()==null?"":account.getPhoneNumber().toString();

                usuario.setIdUsuario (token);
                usuario.setEmail(email);
                usuario.setTelefone(phone);
                usuario.setSenha("123");

                validarLogin();

                existe_conta();
            }
            @Override
            public void onError(AccountKitError accountKitError) {
                finish();
            }
        });
    }

    public existe_conta(){


        usuario.getEmail();
        usuario.getTelefone();

        //criar um codigo para verificar no banco de dados do firebase a essistencia desse Email ou Telefone

        if(true){ // Existe uma contar
            startActivity(new Intent(this, PrincipalActivity.class));
            finish();
        }else{// Não existe uma conta
            startActivity(new Intent(this, CadastroActivity.class));
            finish();
        }


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
                        excecao = "Usuário não está cadastrado.";
                    }catch ( FirebaseAuthInvalidCredentialsException e ){
                        excecao = "E-mail e senha não correspondem a um usuário cadastrado";
                    }catch (Exception e){
                        excecao = "Erro ao cadastrar usuário: "  + e.getMessage();
                        e.printStackTrace();
                    }


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

                        UserStorage.saveUserInfo(getApplicationContext(), user.getNome(), user.getEmail(),user.getImgurl(),user.getTelefone());
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

**/
}

