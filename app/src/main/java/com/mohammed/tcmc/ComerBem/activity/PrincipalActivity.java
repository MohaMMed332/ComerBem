package com.mohammed.tcmc.ComerBem.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.mohammed.tcmc.ComerBem.FirebaseLogin.LoginActivity;
import com.mohammed.tcmc.ComerBem.Menu_Home.About_Menu;
import com.mohammed.tcmc.ComerBem.chat.ChatActivity;
import com.mohammed.tcmc.ComerBem.chat.ListGrupos;
import com.mohammed.tcmc.ComerBem.food.ReceitasActivity;
import com.mohammed.tcmc.ComerBem.Publicar.Edit;
import com.mohammed.tcmc.ComerBem.R;
import com.mohammed.tcmc.ComerBem.config.ConfiguracaoFirebase;

import static com.mohammed.tcmc.ComerBem.R.*;

public class PrincipalActivity extends AppCompatActivity {
    TextView texthome, textHome1; // id textvew
    Button ButtonSingUot;
    Bitmap bitmap;     // pfoto on text
    Animation inm;      //Layout


    private FirebaseAuth autenticacao;
    Dialog myDialog;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_principal);
        myDialog = new Dialog(this);

        texthome = (TextView) findViewById(R.id.TextHome);
        textHome1 = (TextView) findViewById(R.id.TextHome1);


        inm = AnimationUtils.loadAnimation(this, R.anim.fadin);
        texthome.setAnimation(inm);


        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    }


    public void OnClickFood(View view) {
        Intent i = new Intent(this,
                ReceitasActivity.class);
        i.putExtra("patepabo", "aves");
        startActivity(i);
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder;
        alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(
                PrincipalActivity.this, style.CardView));
        alertDialogBuilder.setTitle(getString(string.Confirme_a_saهda));
        alertDialogBuilder.setMessage(string.Tem_certeza_que_quer_sair);
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton(string.Sim, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //PrincipalActivity.super.onBackPressed();
                Log.d("->", getString(string.Entrou));
                finish();
            }
        });

        alertDialogBuilder.setNegativeButton(getString(string.Não), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }



    public void menuClick(View v) {
        ImageView Exit_Menu;
        Button btnfollow;

         myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }


    public void chat(View view) {
        Intent i = new Intent(this, ChatActivity.class);
        startActivity(i);
    }

    public void about_xml(View view) {
        Intent i = new Intent(this, About_Menu.class);
        startActivity(i);
    }

    public void onCklickProfail(View view) {
        Intent i = new Intent(this, ProfailActivity.class);
        startActivity(i);
    }

    public void onclickEdit(View view) {
        Intent i = new Intent(this, Edit.class);
        startActivity(i);
    }

    public void grupoClick(View view) {
        Intent i = new Intent(this, ListGrupos.class);
        startActivity(i);
    }





    public void onCklickExit(View view) {
        AlertDialog.Builder alertDialogBuilder;
        alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(
                PrincipalActivity.this, style.CardView));
        alertDialogBuilder.setTitle(getString(string.Sair_da_conta));
        alertDialogBuilder.setMessage(string.Você_realmente_quer_sair_da_conta);
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton(string.Sim, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //PrincipalActivity.super.onBackPressed();
                Log.d("->", getString(string.Entrou));
                autenticacao.signOut();
                finish();
                Intent i = new Intent(PrincipalActivity.this, LoginActivity.class);
                startActivity(i);
                Toast.makeText(PrincipalActivity.this, getString(string.Sair), Toast.LENGTH_SHORT).show();
            }
        });

        alertDialogBuilder.setNegativeButton(getString(string.Não), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }




}
