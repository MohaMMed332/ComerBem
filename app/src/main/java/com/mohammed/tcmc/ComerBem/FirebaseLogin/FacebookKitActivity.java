package com.mohammed.tcmc.ComerBem.FirebaseLogin;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.mohammed.tcmc.ComerBem.R;
import com.mohammed.tcmc.ComerBem.activity.PrincipalActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FacebookKitActivity extends AppCompatActivity {

    public static int APP_REQUEST_CODE = 99;

   // private final  static  int APP_REQUEST_CODE =999;
    private Button phone_button,email_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_kit);


          phone_button =(Button)findViewById(R.id.phone_button);
         email_button =(Button)findViewById(R.id.email_button);

         email_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        startLoginPage(LoginType.EMAIL); }});

         phone_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        startLoginPage(LoginType.PHONE);
        }});




    }
       private void startLoginPage(LoginType loginType) {
     if (loginType == LoginType.EMAIL)
     { Intent intent=new Intent(this, AccountKitActivity.class);
     AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
     new AccountKitConfiguration.AccountKitConfigurationBuilder(LoginType.EMAIL,
     AccountKitActivity.ResponseType.TOKEN);
     intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,configurationBuilder.build());
     startActivityForResult(intent,APP_REQUEST_CODE); }
     else if (loginType == LoginType.PHONE)
     { Intent intent=new Intent(this, AccountKitActivity.class);
     AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
     new AccountKitConfiguration.AccountKitConfigurationBuilder(LoginType.PHONE,
     AccountKitActivity.ResponseType.TOKEN);
     intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,configurationBuilder.build());
     startActivityForResult(intent,APP_REQUEST_CODE); } }

     @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
     super.onActivityResult(requestCode, resultCode, data);
     if (requestCode == APP_REQUEST_CODE)
     {
     AccountKitLoginResult result = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
     if (result.getError()!=null)
     {
     Toast.makeText(this, ""+result.getError().getErrorType().getMessage(), Toast.LENGTH_SHORT).show();
     return;
     }
     else if (result.wasCancelled())
     {
     Toast.makeText(this,"Cancel", Toast.LENGTH_SHORT).show();
     return;
     }
     else
     {
     if (result.getAccessToken()!= null)
     Toast.makeText(this,"Deliciosas receitas Com Comer Bem"+result.getAccessToken().getAccountId(), Toast.LENGTH_SHORT).show();
     else
     Toast.makeText(this,"Welcome ! %s"+result.getAuthorizationCode().substring(0,10), Toast.LENGTH_SHORT).show();


     startActivity(new Intent(this, PrincipalActivity.class));
     }}}
     private void printKeyHash() {
     try {
     PackageInfo info = getPackageManager().getPackageInfo(
     "edmt.dev.androidfdbaccountkit", PackageManager.GET_SIGNATURES);
     for (Signature signature : info.signatures)
     { MessageDigest md =MessageDigest.getInstance("SHA");
     md.update(signature.toByteArray());
     Log.d("KEYHASH", Base64.encodeToString(md.digest(),Base64.DEFAULT)); }
     } catch (PackageManager.NameNotFoundException e) {
     e.printStackTrace();
     } catch (NoSuchAlgorithmException e) {
     e.printStackTrace();
     }
     }

    public void btEntrar(View view){ startActivity(new Intent(this, LoginActivity.class)); }


}
